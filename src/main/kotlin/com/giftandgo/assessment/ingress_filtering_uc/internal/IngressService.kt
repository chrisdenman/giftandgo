package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_ia.IpApiGateway
import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import com.giftandgo.assessment.ingress_filtering_uc.IngressService
import org.springframework.validation.Validator

class IngressService(
    private val ipApiGateway: IpApiGateway,
    private val validator: Validator,
) : IngressService {
    override fun getIngressDecisionFor(host: String): IngressDecision = ipApiGateway
        .queryBy(host)
        .let { response ->
            ValidationErrors(response, "ipApi")
                .let { validationErrors ->
                    validator.validate(response, validationErrors)
                    IngressDecisionData(validationErrors, response.isp)
                }
        }
}
