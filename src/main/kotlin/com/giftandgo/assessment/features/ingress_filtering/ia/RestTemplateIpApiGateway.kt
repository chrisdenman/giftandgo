package com.giftandgo.assessment.features.ingress_filtering.ia

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
