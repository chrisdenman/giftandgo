package com.giftandgo.assessment.ingress_filtering.uc

import org.springframework.validation.Errors

data class IngressDecisionData(val isp: String?, val e: Errors) : IngressDecision {
    override val errors: Errors = e
    override val isAllowed: Boolean = !errors.hasErrors()
    override val ipProvider: String? = isp
}
