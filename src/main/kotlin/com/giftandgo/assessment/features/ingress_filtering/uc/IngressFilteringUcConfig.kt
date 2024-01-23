package com.giftandgo.assessment.features.ingress_filtering.uc

import com.giftandgo.assessment.features.ingress_filtering.ia.IpApiGateway
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Suppress("ConfigurationProperties")
@Configuration
@ConfigurationProperties(prefix = "app.features.ingress-filtering")
class IngressFilteringUcConfig {

    var blockedCountries: List<String> = listOf()

    var blockedDataCenterOrgs: List<String> = listOf()

    @Bean
    fun ipApiService(ipApiGateway: IpApiGateway): IngressService =
        IngressService(ipApiGateway, IsIngressPermissibleValidator(this))
}
