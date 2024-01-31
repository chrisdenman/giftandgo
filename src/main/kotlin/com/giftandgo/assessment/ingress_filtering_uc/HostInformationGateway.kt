package com.giftandgo.assessment.ingress_filtering_uc

interface HostInformationGateway {
    fun queryBy(host: String): HostInformation
}
