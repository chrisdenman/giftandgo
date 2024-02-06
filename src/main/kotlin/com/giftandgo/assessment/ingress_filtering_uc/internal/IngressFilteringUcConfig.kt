package com.giftandgo.assessment.ingress_filtering_uc.internal

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IngressFilteringUcConfig {

    @Bean
    fun ingressService(
        applicationEventPublisher: ApplicationEventPublisher,
        ingressFilteringUcProperties: IngressFilteringUcProperties
    ): IngressService =
        IngressService(
            HostInformationValidator(
                ingressFilteringUcProperties.blockedCountries,
                ingressFilteringUcProperties.blockedDataCenterOrgs
            )
        )
}
