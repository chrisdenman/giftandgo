package com.giftandgo.assessment.service_history_uc_spring

import com.giftandgo.assessment.service_history_uc_api.ServiceHistoryService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceHistoryUcBeans {

    @Bean
    fun serviceHistoryService(): ServiceHistoryService =
        SpringServiceHistoryService()
}
