package com.giftandgo.assessment.service_history_uc.internal

import com.giftandgo.assessment.service_history_uc.ServiceHistory
import com.giftandgo.assessment.service_history_uc.ServiceHistoryEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

open class ServiceHistoryService {

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
