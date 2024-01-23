package com.giftandgo.assessment.features.service_history.uc

import com.giftandgo.assessment.features.service_history.ia.ServiceHistoryRepository
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

open class ServiceHistoryService(private val serviceHistoryRepository: ServiceHistoryRepository) {

    @Transactional
    open fun saveServiceHistory(
        requestUri: URI,
        receivedAt: LocalDateTime,
        responseCode: HttpStatus,
        remoteAddress: InetAddress,
        requestCountryCode: String,
        addressProvider: String?,
        servicingDurationMs: Duration,
    ) {
        serviceHistoryRepository.save(
            requestUri,
            receivedAt,
            responseCode,
            remoteAddress,
            requestCountryCode,
            addressProvider,
            servicingDurationMs
        )
    }
}
