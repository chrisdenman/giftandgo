package com.giftandgo.assessment.ingress_filtering_ia.internal

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Suppress("ConfigurationProperties")
@Configuration
@ConfigurationProperties(prefix = "app.features.ingress-filtering")
class IngressFilteringIaConfig {

    lateinit var ipApiUrl: String

    @Bean
    fun ipApiRestTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun ipApiGateway(countryRestTemplate: RestTemplate): RestTemplateIpApiGateway =
        RestTemplateIpApiGateway(this, countryRestTemplate)
}