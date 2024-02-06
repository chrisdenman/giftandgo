package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ConditionalOnProperty(name = ["app.features.ingress-filtering.ip-api"], havingValue = "true")
class IpApiIngressFilteringConfig {

    @Bean
    fun ipApiRestTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun ipApiGateway(
        countryRestTemplate: RestTemplate,
        ipApiIngressFilteringProperties: IpApiIngressFilteringProperties
    ) =
        IpApiRestTemplateGateway(ipApiIngressFilteringProperties.url, countryRestTemplate)
}
