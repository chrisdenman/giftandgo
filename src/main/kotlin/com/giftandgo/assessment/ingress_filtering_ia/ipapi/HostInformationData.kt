package com.giftandgo.assessment.ingress_filtering_ia.ipapi

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.giftandgo.assessment.ingress_filtering_uc.HostInformation

@JsonIgnoreProperties(ignoreUnknown = true)
data class HostInformationData(
    override val status: String,
    override val countryCode: String?,
    override val hosting: Boolean?,
    override val org: String?,
    override val isp: String?
) : HostInformation
