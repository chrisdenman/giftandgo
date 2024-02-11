package com.giftandgo.assessment.service_history_uc_spring

import com.giftandgo.assessment.service_history_uc_api.ServiceHistory
import com.giftandgo.assessment.service_history_uc_api.ServiceHistoryEvent
import com.giftandgo.assessment.service_history_uc_api.ServiceHistoryService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class SpringServiceHistoryService : ServiceHistoryService {

    @EventListener
    @Async
    open fun on(serviceHistoryEvent: ServiceHistoryEvent): ServiceHistory =
        serviceHistoryEvent.let {
            object : ServiceHistory {
                override val id: String? = null
                override val requestUri = it.requestUri
                override val receivedAt = it.receivedAt
                override val statusCode = it.responseCode
                override val requestIpAddress = it.remoteAddress
                override val requestCountryCode = it.requestCountryCode
                override val ipProvider: String? = it.addressProvider
                override val servicingDurationMs = it.servicingDurationMs
            }
        }
}
