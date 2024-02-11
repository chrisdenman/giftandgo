package com.giftandgo.assessment.ingress_filtering.ip_api

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["app.features.ingress-filtering.ip-api"], havingValue = "true")
class IpApiBeans {

    @Bean
    fun ipApiGateway(
        ipApiProperties: IpApiProperties,
        builder: RestTemplateBuilder
    ): HostInformationGateway =
        IpApiRestTemplateGateway(
            builder.rootUri(ipApiProperties.url.toString()).build()
        )
}
