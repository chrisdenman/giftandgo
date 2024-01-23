package com.giftandgo.assessment.features.ingress_filtering.ia

interface IpApiResponse {
    val status: String
    val countryCode: String?
    val hosting: Boolean?
    val org: String?
    val isp: String?
    val success: Boolean
}
