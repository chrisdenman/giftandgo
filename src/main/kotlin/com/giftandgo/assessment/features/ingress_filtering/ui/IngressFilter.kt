package com.giftandgo.assessment.features.ingress_filtering.ui

import com.fasterxml.jackson.databind.ObjectMapper
import com.giftandgo.assessment.features.ingress_filtering.uc.IngressDecision
import com.giftandgo.assessment.features.ingress_filtering.uc.IngressService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


open class IngressFilter(
    private val ingressService: IngressService,
    private val remoteHostResolver: RemoteHostResolver? = null
) : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ATTRIBUTE__IP_PROVIDER = "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
        const val HTTP_HEADER__X_FORWARDED_FOR = "x-forwarded-for"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        ingressService
            .getIngressDecisionFor(getOriginHost(request))
            .let { ingressionDecision ->
                when (ingressionDecision.isAllowed) {
                    true -> {
                        filterChain.doFilter(
                            request.also {
                                it.setAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER, ingressionDecision.ipProvider)
                            },
                            response
                        )
                    }

                    false -> handleIngressRejection(response, ingressionDecision)
                }
            }
    }

    private fun handleIngressRejection(response: HttpServletResponse, ingressDecision: IngressDecision) {
        val content = mapOf("errors" to
            ingressDecision
                .errors
                .allErrors
                .map { it.defaultMessage ?: "" }
        )
        response.contentType = "application/json"
        response.status = FORBIDDEN.value()
        response.writer.write(ObjectMapper().writeValueAsString(content))
    }


    private fun getOriginHost(httpServletRequest: HttpServletRequest): String =
        httpServletRequest.run {
            remoteHostResolver?.resolve(this) ?: when ((HTTP_HEADER__X_FORWARDED_FOR in headerNames.toList())) {
                true -> getHeaders(HTTP_HEADER__X_FORWARDED_FOR).nextElement()
                false -> remoteHost
            }
        }
}
