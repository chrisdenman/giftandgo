package com.giftandgo.assessment.features.ingress_filtering.ia

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IpApiResponseData(

    override val status: String,

    override val countryCode: String?,
    override val hosting: Boolean?,
    override val org: String?,
    override val isp: String?
) : IpApiResponse {
    override val success: Boolean = status == "success"
}
