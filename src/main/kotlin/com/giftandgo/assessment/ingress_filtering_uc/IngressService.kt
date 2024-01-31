package com.giftandgo.assessment.ingress_filtering_uc

interface IngressService {
    fun getDecisionFor(host: String): IngressDecision
}
