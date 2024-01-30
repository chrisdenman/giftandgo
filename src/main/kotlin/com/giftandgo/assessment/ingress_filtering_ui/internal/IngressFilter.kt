package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.fasterxml.jackson.databind.ObjectMapper
import com.giftandgo.assessment.ingress_filtering_uc.IngressService
import com.giftandgo.assessment.ingress_filtering_ui.RemoteHostResolver
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS


open class IngressFilter(
    private val ingressService: IngressService,
    private val messageSource: MessageSource,
    private val remoteHostResolver: RemoteHostResolver? = null
) : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ATTRIBUTE__IP_PROVIDER = "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
        const val HTTP_HEADER__X_FORWARDED_FOR = "x-forwarded-for"
        const val MAX__TIME_FOR_INGRESSION_DECISION__SECONDS =
            3L // @todo config on the service or is it a use case thingy
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val executor = Executors.newSingleThreadExecutor()
        try {
            executor
                .submit(
                    Callable {
                        ingressService.getIngressDecisionFor(getOriginHost(request))
                    }
                )
                .get(MAX__TIME_FOR_INGRESSION_DECISION__SECONDS, SECONDS)!!
                .let { ingressDecision ->
                    when (ingressDecision.isAllowed) {
                        true -> {
                            filterChain.doFilter(
                                request.apply {
                                    setAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER, ingressDecision.ipProvider)
                                },
                                response
                            )
                        }

                        false -> response.apply {
                            contentType = "application/json"
                            status = FORBIDDEN.value()
                            writer.write(
                                ObjectMapper()
                                    .writeValueAsString(
                                        mapOf<String, Any>(
                                            "created" to listOf<String>(),
                                            "errors" to ingressDecision.errors.resolveMessages(messageSource)
                                        )
                                    )
                            )
                        }
                    }
                }
        } finally {
            executor.shutdown()
        }
    }

    private fun getOriginHost(httpServletRequest: HttpServletRequest): String =
        httpServletRequest.run {
            remoteHostResolver
                ?.resolve(this) ?: when ((HTTP_HEADER__X_FORWARDED_FOR in headerNames.toList()
                .map { it?.lowercase() })) {
                true -> getHeaders(HTTP_HEADER__X_FORWARDED_FOR).nextElement()
                false -> remoteHost
            }
        }
}
