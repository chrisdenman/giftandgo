package com.giftandgo.assessment.service_history_uc.internal

import com.giftandgo.assessment.service_history_uc.ServiceHistoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceHistoryUcConfig {

    @Bean
    fun serviceHistoryService(
        serviceHistoryRepository: ServiceHistoryRepository
    ): ServiceHistoryService = ServiceHistoryService()
}
