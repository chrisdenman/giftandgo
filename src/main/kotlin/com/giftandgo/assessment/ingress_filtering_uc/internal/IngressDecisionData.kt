package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import org.springframework.validation.Errors

data class IngressDecisionData(val e: Errors, val isp: String?) : IngressDecision {
    override val errors: Errors = e
    override val isAllowed: Boolean = !errors.hasErrors()
    override val ipProvider: String? = isp
}
