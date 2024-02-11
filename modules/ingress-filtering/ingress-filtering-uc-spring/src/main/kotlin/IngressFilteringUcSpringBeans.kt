package com.giftandgo.assessment.ingress_filtering_uc_spring

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IngressFilteringUcSpringBeans {

    @Bean
    fun ingressService(
        applicationEventPublisher: ApplicationEventPublisher,
        ingressFilteringUcSpringProperties: IngressFilteringUcSpringProperties
    ): IngressService =
        ingressFilteringUcSpringProperties.run {
            IngressService(
                HostInformationValidator(blockedCountries, blockedDataCenterOrgs)
            )
        }
}
