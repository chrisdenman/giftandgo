package com.giftandgo.assessment.ingress_filtering_uc

interface HostInformationQuery : CorrelatedApplicationEvent {
    val host: String
}
