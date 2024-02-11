package com.giftandgo.assessment.ingress_filtering.ip_api

import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.web.client.RestTemplate

internal open class IpApiRestTemplateGateway(
    private val restTemplate: RestTemplate
) : HostInformationGateway {
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
                        "/json/${hostInformationQuery.host}?fields=status,countryCode,hosting,org,isp",
                        IpApiHostData::class.java
                    )!!
                    .run {
                        HostInformationData(hosting, org, isp, countryCode, hostInformationQuery.correlationId)
                    }
                    .also { logger.info("Publishing $it") }
            }
}
