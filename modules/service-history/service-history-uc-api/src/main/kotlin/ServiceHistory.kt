package com.giftandgo.assessment.service_history_uc_api

import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime

interface ServiceHistory {
    val id: String?
    val requestUri: URI
    val receivedAt: LocalDateTime
    val statusCode: Int
    val requestIpAddress: InetAddress
    val requestCountryCode: String
    val ipProvider: String?
    val servicingDurationMs: Duration
}
