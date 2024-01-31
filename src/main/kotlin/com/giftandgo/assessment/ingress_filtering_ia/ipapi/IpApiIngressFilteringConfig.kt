package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import com.giftandgo.assessment.ingress_filtering_uc.HostInformationGateway
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.net.URI

@Configuration
@ConfigurationProperties(prefix = "app.features.ingress-filtering.ip-api")
@ConditionalOnProperty(name = ["app.features.ingress-filtering.ip-api"], havingValue = "true")
class IpApiIngressFilteringConfig {

    lateinit var url: URI

    @Bean
    fun ipApiRestTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

    @Bean
    fun ipApiGateway(countryRestTemplate: RestTemplate): HostInformationGateway =
        IpApiRestTemplateGateway(url, countryRestTemplate)
}
