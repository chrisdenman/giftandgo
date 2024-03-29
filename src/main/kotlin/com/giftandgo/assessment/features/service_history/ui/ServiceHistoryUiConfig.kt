package com.giftandgo.assessment.features.service_history.ui

import com.giftandgo.assessment.features.service_history.uc.ServiceHistoryService
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class ServiceHistoryUiConfig {

    @Bean("serviceHistoryFilter")
    fun serviceHistoryFilterNormal(serviceHistoryService: ServiceHistoryService): Filter =
        ServiceHistoryFilter(serviceHistoryService)

    @Bean
    fun persistApiResponseFilterRegistration(serviceHistoryFilter: Filter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = serviceHistoryFilter
            order = Ordered.HIGHEST_PRECEDENCE
            setName("Api Response Filter")
            addUrlPatterns("/peopleSpeedData")
        }
}
