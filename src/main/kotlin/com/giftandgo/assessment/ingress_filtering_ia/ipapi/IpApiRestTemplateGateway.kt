package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import com.giftandgo.assessment.ingress_filtering_ia.internal.HostInformationData
import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.web.client.RestTemplate
import java.net.URI

open class IpApiRestTemplateGateway(
    private val url: URI,
    private val restTemplate: RestTemplate
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(IpApiRestTemplateGateway::class.java)
    }

    @EventListener
    @Async
    open fun on(hostInformationQuery: HostInformationQuery): HostInformation =
        logger
            .info("Received $hostInformationQuery")
            .run {
                restTemplate
                    .getForObject(
                        "${url}/json/${hostInformationQuery.host}?fields=status,countryCode,hosting,org,isp",
                        IpApiHostData::class.java
                    )!!
                    .run {
                        HostInformationData(hosting, org, isp, countryCode, hostInformationQuery.correlationId)
                    }
                    .also { logger.info("Publishing $it") }
            }
}
