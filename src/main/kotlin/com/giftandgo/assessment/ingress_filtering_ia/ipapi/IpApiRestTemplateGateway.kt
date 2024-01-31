package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationGateway
import org.springframework.web.client.RestTemplate
import java.net.URI

class IpApiRestTemplateGateway(
    private val url: URI,
    private val restTemplate: RestTemplate
) : HostInformationGateway {
    override fun queryBy(host: String): HostInformation =
        restTemplate
            .getForObject(
                "${url}/json/$host?fields=status,countryCode,hosting,org,isp",
                HostInformationData::class.java
            )!!
}
