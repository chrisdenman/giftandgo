package com.giftandgo.assessment.features.service_history.ui

import com.giftandgo.assessment.features.service_history.uc.ServiceHistoryService
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.util.StopWatch
import java.io.IOException
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

open class ServiceHistoryFilter(private val serviceHistoryService: ServiceHistoryService, ) : Filter {

    companion object {
        const val REQUEST_ATTRIBUTE__IP_PROVIDER = "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val timeMeasure = StopWatch()
        try {
            timeMeasure.start("Service /peopleSpeedData requests")
            chain.doFilter(request, response)
            timeMeasure.stop()
        } finally {
            serviceHistoryService.saveServiceHistory(
                URI.create((request as HttpServletRequest).requestURI),
                LocalDateTime.now(),
                HttpStatus.resolve((response as HttpServletResponse).status)!!,
                InetAddress.getByName(request.remoteAddr),
                request.locale.country,
                request.getAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER) as? String,
                Duration.ofMillis(timeMeasure.totalTimeMillis)
            )
        }
    }
}
