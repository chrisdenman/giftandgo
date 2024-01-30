package com.giftandgo.assessment.ingress_filtering_ia.internal

import com.giftandgo.assessment.ingress_filtering_ia.IpApiGateway
import com.giftandgo.assessment.ingress_filtering_ia.IpApiResponse
import org.springframework.web.client.RestTemplate

class RestTemplateIpApiGateway(
    private val ingressFilteringIaConfig: IngressFilteringIaConfig,
    private val ipApiRestTemplate: RestTemplate
) : IpApiGateway {
    override fun queryBy(host: String): IpApiResponse =
        ipApiRestTemplate
            .getForObject(
                "${ingressFilteringIaConfig.ipApiUrl}/json/$host?fields=status,countryCode,hosting,org,isp",
                IpApiResponseData::class.java
            )!!
}
