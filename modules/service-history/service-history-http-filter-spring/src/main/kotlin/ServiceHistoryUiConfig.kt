package com.giftandgo.assessment.service_history_http_filter_spring

import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class ServiceHistoryUiConfig {

    @Bean("serviceHistoryFilter")
    fun serviceHistoryFilter(applicationEventPublisher: ApplicationEventPublisher): Filter =
        ServiceHistoryFilter(applicationEventPublisher)

    @Bean
    fun persistApiResponseFilterRegistration(serviceHistoryFilter: Filter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = serviceHistoryFilter
            order = Ordered.HIGHEST_PRECEDENCE
            setName("Api Response Filter")
            addUrlPatterns("/peopleSpeedData")
        }
}
