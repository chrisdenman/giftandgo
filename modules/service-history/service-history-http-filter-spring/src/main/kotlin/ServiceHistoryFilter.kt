package com.giftandgo.assessment.service_history_http_filter_spring

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.util.StopWatch
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

open class ServiceHistoryFilter(private val applicationEventPublisher: ApplicationEventPublisher) :
    OncePerRequestFilter() {

    companion object {
        const val REQUEST_ATTRIBUTE__IP_PROVIDER = "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        logger.info("Service History filtering")
        val stopWatch = StopWatch()
        try {
            stopWatch.start("Service /peopleSpeedData requests")
            chain.doFilter(request, response)
            stopWatch.stop()
        } finally {
            applicationEventPublisher.publishEvent(
                ServiceHistoryEventData(
                    URI.create(request.requestURI),
                    LocalDateTime.now(),
                    response.status,
                    InetAddress.getByName(request.remoteAddr),
                    request.locale.country,
                    Duration.ofMillis(stopWatch.totalTimeMillis),
                    request.getAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER) as? String
                )
            )
        }
    }
}
