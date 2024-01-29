package com.giftandgo.assessment.ingress_filtering.ui

import com.giftandgo.assessment.ingress_filtering.uc.IngressService
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class IngressFilteringUiConfig {

    @Bean
    fun ingressFilter(
        ingressService: IngressService,
        messageSource: MessageSource
    ): Filter = IngressFilter(ingressService, messageSource)

    @Bean
    fun ingressFilterRegistration(ingressFilter: Filter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = ingressFilter
            order = Ordered.HIGHEST_PRECEDENCE + 1000
            setName("IP Address Filter")
            addUrlPatterns("/peopleSpeedData")
        }
}
