package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_ia.IpApiGateway
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Suppress("ConfigurationProperties")
@Configuration
@ConfigurationProperties(prefix = "app.features.ingress-filtering")
class IngressFilteringUcConfig {

    lateinit var blockedCountries: List<String>

    lateinit var blockedDataCenterOrgs: List<String>

    @Bean
    fun ipApiService(ipApiGateway: IpApiGateway): IngressService =
        IngressService(ipApiGateway, IpApiResponseValidator(this))
}
