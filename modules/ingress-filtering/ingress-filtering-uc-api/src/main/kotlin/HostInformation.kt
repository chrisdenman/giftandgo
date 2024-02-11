package com.giftandgo.assessment.ingress_filtering_uc

interface HostInformation : CorrelatedApplicationEvent {
    val countryCode: String?
    val hosting: Boolean?
    val org: String?
    val isp: String?
}
