package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.util.StopWatch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class EventTransceiver<S : CorrelatedApplicationEvent, R : CorrelatedApplicationEvent>(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val executorService: ExecutorService,
    private val decisionTimeOutMilliSeconds: Long
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventTransceiver::class.java)
    }

    private var request: S? = null
    private var response: R? = null
    private var responseClass: Class<R>? = null
    private val guard = CountDownLatch(1)

    fun awaitResponseTo(event: S, responseType: Class<R>): R {
        logger.info("Publishing $event")

        request = event
        response = null
        responseClass = responseType

        val stopWatch = StopWatch().apply { start() }
        executorService
            .submit { applicationEventPublisher.publishEvent(event as Any) }
            .get(decisionTimeOutMilliSeconds, TimeUnit.MILLISECONDS)
        stopWatch.stop()
        val millisRemaining = decisionTimeOutMilliSeconds - stopWatch.totalTimeMillis

        logger.info("${millisRemaining}mS remaining from total of ${decisionTimeOutMilliSeconds}mS")

        stopWatch.start()
        if (this.guard.await(millisRemaining, TimeUnit.MILLISECONDS)) {
            stopWatch.stop()
            logger.info(
                "${decisionTimeOutMilliSeconds - stopWatch.totalTimeMillis}mS used from total of " +
                    "${decisionTimeOutMilliSeconds}mS"
            )
            return response!!
        } else {
            throw TimeoutException()
        }
    }

    @EventListener
    fun on(response: R) {
        request?.run {
            if (
                (correlationId == response.correlationId) &&
                (responseClass!!.isAssignableFrom(response.javaClass))
            ) {
                logger.info("Handling matching $response")
                this@EventTransceiver.response = response
                guard.countDown()
            }
        }
    }
}
