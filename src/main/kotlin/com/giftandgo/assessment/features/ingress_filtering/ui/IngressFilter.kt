package com.giftandgo.assessment.features.ingress_filtering.ui

import com.giftandgo.assessment.features.ingress_filtering.uc.IngressService
import com.giftandgo.assessment.features.service_history.ui.RemoteHostResolver
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus.FORBIDDEN
import java.io.IOException

open class IngressFilter(
    private val ingressService: IngressService,
    private val remoteHostResolver: RemoteHostResolver? = null
) : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        ingressService
            .getIngressDecisionFor(getOriginHost(request as HttpServletRequest))
            .let { ingressionDecision ->
                when (ingressionDecision.isAllowed) {
                    true -> {
                        request.setAttribute("292e035f-e160-4c34-a427-a27096245d37", ingressionDecision.ipProvider)
                        chain.doFilter(request, response)
                    }

                    false ->
                        (response as HttpServletResponse)
                            .sendError(FORBIDDEN.value(),
                                ingressionDecision
                                    .errors
                                    .allErrors
                                    .map { it.defaultMessage }
                                    .joinToString()
                            )
                }
            }
    }

    private fun getOriginHost(httpServletRequest: HttpServletRequest): String =
        remoteHostResolver?.resolve(httpServletRequest) ?:
            httpServletRequest.getHeaders("HTTP_X_FORWARDED_FOR").toList().let {
                if (it.isEmpty()) {
                    httpServletRequest.remoteHost
                } else {
                    it[0]
                }
            }
}
