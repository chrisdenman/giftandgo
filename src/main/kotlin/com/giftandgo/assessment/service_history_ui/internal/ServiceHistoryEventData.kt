package com.giftandgo.assessment.service_history_ui.internal

import com.giftandgo.assessment.service_history_uc.ServiceHistoryEvent
import org.springframework.http.HttpStatus
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

data class ServiceHistoryEventData(
    override val requestUri: URI,
    override val receivedAt: LocalDateTime,
    override val responseCode: HttpStatus,
    override val remoteAddress: InetAddress,
    override val requestCountryCode: String,
    override val servicingDurationMs: Duration,
    override val addressProvider: String?
) : ServiceHistoryEvent
