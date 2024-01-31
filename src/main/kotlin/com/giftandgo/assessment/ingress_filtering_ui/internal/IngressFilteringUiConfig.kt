package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.giftandgo.assessment.ingress_filtering_uc.IngressService
import jakarta.servlet.Filter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
@ConditionalOnProperty(name = ["app.features.ingress-filtering"], havingValue = "true")
class IngressFilteringUiConfig {

    @Bean
    fun ingressFilter(ingressService: IngressService, messageSource: MessageSource): Filter =
        IngressFilter(ingressService, messageSource)

    @Bean
    fun ingressFilterRegistration(ingressFilter: Filter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = ingressFilter
            order = Ordered.HIGHEST_PRECEDENCE + 1000
            setName("IP Address Filter")
            addUrlPatterns("/peopleSpeedData") // @todo change to wildcards
        }
}
