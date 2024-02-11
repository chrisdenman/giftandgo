package com.giftandgo.assessment.ingress_filtering.ip_api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class IpApiHostData(
    val status: String,
    val countryCode: String?,
    val hosting: Boolean?,
    val org: String?,
    val isp: String?
)
