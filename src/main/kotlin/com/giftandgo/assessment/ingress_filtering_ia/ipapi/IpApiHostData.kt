package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IpApiHostData(
    val status: String,
    val countryCode: String?,
    val hosting: Boolean?,
    val org: String?,
    val isp: String?
)
