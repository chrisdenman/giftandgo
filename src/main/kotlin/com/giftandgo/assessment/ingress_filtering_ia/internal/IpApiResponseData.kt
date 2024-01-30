package com.giftandgo.assessment.ingress_filtering_ia.internal

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.giftandgo.assessment.ingress_filtering_ia.IpApiResponse

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
