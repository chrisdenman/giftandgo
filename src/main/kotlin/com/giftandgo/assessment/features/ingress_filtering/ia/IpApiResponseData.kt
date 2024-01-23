package com.giftandgo.assessment.features.ingress_filtering.ia

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.validation.constraints.Pattern

@JsonIgnoreProperties(ignoreUnknown = true)
data class IpApiResponseData(

    @field:Pattern(regexp = "success")
    override val status: String,

    override val countryCode: String?,
    override val hosting: Boolean?,
    override val org: String?,
    override val isp: String?
) : IpApiResponse {
    override val success: Boolean
        get() = status == "success"
}
