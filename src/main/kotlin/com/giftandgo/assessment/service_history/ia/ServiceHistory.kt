package com.giftandgo.assessment.service_history.ia

import org.springframework.http.HttpStatus
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

interface ServiceHistory {
    val id: String
    val requestUri: URI
    val receivedAt: LocalDateTime
    val statusCode: HttpStatus
    val requestIpAddress: InetAddress
    val requestCountryCode: String
    val ipProvider: String?
    val servicingDurationMs: Duration
}
