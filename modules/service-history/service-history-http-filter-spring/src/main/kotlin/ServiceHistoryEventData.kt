package com.giftandgo.assessment.service_history_http_filter_spring

import com.giftandgo.assessment.service_history_uc_api.ServiceHistoryEvent
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

internal data class ServiceHistoryEventData(
    override val requestUri: URI,
    override val receivedAt: LocalDateTime,
    override val responseCode: Int,
    override val remoteAddress: InetAddress,
    override val requestCountryCode: String,
    override val servicingDurationMs: Duration,
    override val addressProvider: String?
) : ServiceHistoryEvent
