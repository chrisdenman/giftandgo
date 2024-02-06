package com.giftandgo.assessment.service_history_uc

import org.springframework.http.HttpStatus
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

interface ServiceHistoryEvent {
    val requestUri: URI
    val receivedAt: LocalDateTime
    val responseCode: HttpStatus
    val remoteAddress: InetAddress
    val requestCountryCode: String
    val servicingDurationMs: Duration
    val addressProvider: String?
}
