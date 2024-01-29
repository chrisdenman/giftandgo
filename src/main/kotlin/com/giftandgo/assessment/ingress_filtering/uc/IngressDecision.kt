package com.giftandgo.assessment.ingress_filtering.uc

import org.springframework.validation.Errors

interface IngressDecision {

    val isAllowed: Boolean

    val ipProvider: String?

    val errors: Errors
}
