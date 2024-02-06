package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.util.StopWatch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class AsyncRequestResponsePublisher<S : CorrelatedApplicationEvent, R : CorrelatedApplicationEvent>(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val executorService: ExecutorService,
    private val decisionTimeOutMilliSeconds: Long
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AsyncRequestResponsePublisher::class.java)

        private data class State<S : CorrelatedApplicationEvent, C : CorrelatedApplicationEvent>(
            val request: S,
            val responseType: Class<C>,
            val guard: CountDownLatch = CountDownLatch(1),
            val stopWatch: StopWatch = StopWatch(),
            var response: C? = null
        )
    }

    private val messageCorrelationIdToState = ConcurrentHashMap<String, State<S, R>>()

    fun publishAndBlockFor(
        event: S,
        responseType: Class<R>
    ): R {
        logger.info("Publishing $event")

        with(State(event, responseType, CountDownLatch(1))) {
            messageCorrelationIdToState[event.correlationId] = this
            try {
                stopWatch.start()
                executorService
                    .submit { applicationEventPublisher.publishEvent(event) }
                    .get(decisionTimeOutMilliSeconds, TimeUnit.MILLISECONDS)
                stopWatch.stop()

                val millisRemaining = decisionTimeOutMilliSeconds - stopWatch.totalTimeMillis
                logger.info("${stopWatch.totalTimeMillis}mS/${decisionTimeOutMilliSeconds}mS used")
                stopWatch.start()

                if (guard.await(millisRemaining, TimeUnit.MILLISECONDS)) {
                    stopWatch.stop()
                    logger.info("${stopWatch.totalTimeMillis}mS/${decisionTimeOutMilliSeconds}mS used")
                    return response!!
                } else {
                    throw TimeoutException()
                }
            } finally {
                messageCorrelationIdToState.remove(event.correlationId)
                logger.info("Awaiting ${messageCorrelationIdToState.size} responses")
            }
        }
    }

    @EventListener
    fun on(response: R) {
        messageCorrelationIdToState[response.correlationId]?.apply {
            if (responseType.isAssignableFrom(response.javaClass)) {
                logger.info("Handling matching $response")
                this.response = response
                guard.countDown()
            }
        }
    }
}
