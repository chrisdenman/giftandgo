package com.giftandgo.assessment.features.service_history.uc;

import com.giftandgo.assessment.features.service_history.ia.ServiceHistoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceHistoryUcConfig {

    @Bean
    fun serviceHistoryService(serviceHistoryRepository: ServiceHistoryRepository): ServiceHistoryService =
        ServiceHistoryService(serviceHistoryRepository)
}
