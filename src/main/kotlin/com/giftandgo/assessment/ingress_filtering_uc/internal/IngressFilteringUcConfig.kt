package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.HostInformationGateway
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.features.ingress-filtering")
class IngressFilteringUcConfig {

    lateinit var blockedCountries: List<String>

    lateinit var blockedDataCenterOrgs: List<String>

    @Bean
    fun ingressService(hostInformationGateway: HostInformationGateway): IngressService =
        IngressService(
            hostInformationGateway,
            HostInformationValidator(blockedCountries, blockedDataCenterOrgs)
        )
}
