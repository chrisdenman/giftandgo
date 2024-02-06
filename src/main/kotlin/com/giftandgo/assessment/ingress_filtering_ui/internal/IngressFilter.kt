package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.fasterxml.jackson.databind.ObjectMapper
import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

open class IngressFilter(
    private val messageSource: MessageSource,
    private val publisher: AsyncRequestResponsePublisher<IngressQueryEvent, IngressDecision>,
    private val remoteHostResolver: RemoteHostResolver? = null
) : OncePerRequestFilter() {

    companion object {
        private const val REQUEST_ATTRIBUTE__IP_PROVIDER =
            "INGRESS_FILTER_IP_PROVIDER=292e035f-e160-4c34-a427-a27096245d37"
        private const val HTTP_HEADER__X_FORWARDED_FOR = "x-forwarded-for"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("Ingress filtering")
        val ingressDecision: IngressDecision = publisher
            .publishAndBlockFor(IngressQueryEvent(getOriginHost(request)), IngressDecision::class.java)

        when (ingressDecision.isAllowed) {
            true -> {
                filterChain.doFilter(
                    request.apply {
                        setAttribute(REQUEST_ATTRIBUTE__IP_PROVIDER, ingressDecision.isp)
                    },
                    response
                )
            }

            false -> response.apply {
                contentType = "application/json"
                status = HttpStatus.FORBIDDEN.value()
                writer.write(
                    ObjectMapper()
                        .writeValueAsString(
                            mapOf<String, Any>(
                                "created" to listOf<String>(),
                                "errors" to
                                    ingressDecision.errorCodes.map { messageSource.getMessage(it, null, locale) }
                            )
                        )
                )
            }
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
