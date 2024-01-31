package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.HostInformationGateway
import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import com.giftandgo.assessment.ingress_filtering_uc.IngressService
import org.springframework.validation.Validator

class IngressService(
    private val hostInformationGateway: HostInformationGateway,
    private val validator: Validator,
) : IngressService {
    override fun getDecisionFor(host: String): IngressDecision = hostInformationGateway
        .queryBy(host)
        .let { response ->
            ValidationErrors(response, "ipApi")
                .let { validationErrors ->
                    validator.validate(response, validationErrors)
                    IngressDecisionData(validationErrors, response.isp)
                }
        }
}
