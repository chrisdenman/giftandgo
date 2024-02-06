package com.giftandgo.assessment.ingress_filtering_uc

interface CorrelatedApplicationEvent {
    val id: String
    val correlationId: String
}
