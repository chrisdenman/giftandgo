package com.giftandgo.assessment.features.ingress_filtering.ia

import org.springframework.web.client.RestTemplate

class RestTemplateIpApiGateway(
    private val ingressFilteringIaConfig: IngressFilteringIaConfig,
    private val ipApiRestTemplate: RestTemplate
) : IpApiGateway {
    override fun queryBy(host: String): IpApiResponse =
        ipApiRestTemplate
            .getForObject(
                String.format(ingressFilteringIaConfig.ipApiUrlPattern, host),
                IpApiResponseData::class.java
            )!!
}
