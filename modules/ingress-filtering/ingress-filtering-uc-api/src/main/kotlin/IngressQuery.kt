package com.giftandgo.assessment.ingress_filtering_uc

interface IngressQuery : CorrelatedApplicationEvent {
    val host: String
}
