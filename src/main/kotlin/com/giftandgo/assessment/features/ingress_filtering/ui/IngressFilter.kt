package com.giftandgo.assessment.features.ingress_filtering.ui

import com.giftandgo.assessment.features.ingress_filtering.uc.IngressService
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

    companion object {
        const val REQUEST_ATTRIBUTE__IP_PROVIDER = "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
        const val HTTP_HEADER__X_FORWARDED_FOR = "x-forwarded-for"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        ingressService
            .getIngressDecisionFor(getOriginHost(request as HttpServletRequest))
            .let { ingressionDecision ->
                when (ingressionDecision.isAllowed) {
                    true -> {
                        chain.doFilter(
                            request.also {
                                it.setAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER, ingressionDecision.ipProvider)
                            },
                            response
                        )
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
        httpServletRequest.run {
            remoteHostResolver?.resolve(this) ?: when ((HTTP_HEADER__X_FORWARDED_FOR in headerNames.toList())) {
                true -> getHeaders(HTTP_HEADER__X_FORWARDED_FOR).nextElement()
                false -> remoteHost
            }
        }
}
