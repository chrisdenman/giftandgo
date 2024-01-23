package com.giftandgo.assessment.features.service_history.ia

import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpStatus
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

@JvmDefaultWithCompatibility
interface ServiceHistoryRepository : CrudRepository<ServiceHistoryEntity, Long> {

    fun save(
        requestUri: URI,
        receivedAt: LocalDateTime,
        responseCode: HttpStatus,
        remoteAddress: InetAddress,
        requestCountryCode: String,
        addressProvider: String?,
        servicingDurationMs: Duration,
    ): ServiceHistory =
        save(
            ServiceHistoryEntity(
                requestUri,
                receivedAt,
                responseCode,
                remoteAddress,
                requestCountryCode,
                addressProvider,
                servicingDurationMs
            )
        )
}
