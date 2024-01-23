package com.giftandgo.assessment.features.ingress_filtering.ui;

import com.giftandgo.assessment.features.ingress_filtering.uc.IngressService
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class IngressFilteringUiConfig {

    @Bean
    fun ingressFilter(ingressService: IngressService): Filter = IngressFilter(ingressService)

    @Bean
    fun ingressFilterRegistration(ingressService: IngressService): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = ingressFilter(ingressService)
            order = Ordered.HIGHEST_PRECEDENCE + 1000
            setName("IP Address Filter")
            addUrlPatterns("/peopleSpeedData")
        }
}
