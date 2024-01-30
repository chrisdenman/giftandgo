package com.giftandgo.assessment.ingress_filtering_uc

interface IngressService {
    fun getIngressDecisionFor(host: String): IngressDecision
}
