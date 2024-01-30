package com.giftandgo.assessment.ingress_filtering_ia

interface IpApiResponse {
    val status: String
    val countryCode: String?
    val hosting: Boolean?
    val org: String?
    val isp: String?
    val success: Boolean
}
