package com.giftandgo.assessment.features.ingress_filtering.uc

import com.giftandgo.assessment.features.ingress_filtering.ia.IpApiGateway
import org.springframework.validation.Validator

class IngressService(
    private val ipApiGateway: IpApiGateway,
    private val validator: Validator,
) {
    fun getIngressDecisionFor(host: String): IngressDecision =
        ipApiGateway
            .queryBy(host)
            .let { IngressDecisionData(it.isp, validator.validateObject(it)) }
}
