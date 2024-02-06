package com.giftandgo.assessment.service_history_ia

import com.giftandgo.assessment.service_history_ia.internal.ServiceHistoryEntity
import com.giftandgo.assessment.service_history_uc.ServiceHistory
import com.giftandgo.assessment.service_history_uc.ServiceHistoryRepository
import org.springframework.context.event.EventListener
import org.springframework.data.repository.CrudRepository
import org.springframework.scheduling.annotation.Async

@Suppress("unused")
@JvmDefaultWithCompatibility
interface ServiceHistoryCrudRepository : CrudRepository<ServiceHistoryEntity, Long>, ServiceHistoryRepository {

    @EventListener
    @Async
    override fun on(serviceHistory: ServiceHistory): Unit =
        serviceHistory.run {
            ServiceHistoryEntity(
                requestUri,
                receivedAt,
                statusCode,
                requestIpAddress,
                requestCountryCode,
                ipProvider,
                servicingDurationMs
            ).let { save(it) }
        }
}
