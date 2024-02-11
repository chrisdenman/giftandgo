package com.giftandgo.assessment.ingress_filtering_uc

interface IngressDecision : CorrelatedApplicationEvent {

    val isAllowed: Boolean

    val isp: String?

    val errorCodes: Iterable<String>
}
