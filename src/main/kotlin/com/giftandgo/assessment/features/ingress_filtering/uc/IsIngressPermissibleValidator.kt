package com.giftandgo.assessment.features.ingress_filtering.uc

import com.giftandgo.assessment.features.ingress_filtering.ia.IpApiResponse
import org.springframework.validation.Errors
import org.springframework.validation.Validator

class IsIngressPermissibleValidator(private val ingressFilteringUcConfig: IngressFilteringUcConfig) : Validator {

    override fun supports(clazz: Class<*>): Boolean =
        IpApiResponse::class.java == clazz

    override fun validate(target: Any, errors: Errors) {
        if ((target as IpApiResponse).status != "success") {
            errors.reject(
                "status.unacceptable",
                "Your host is unacceptable."
            )
        } else {
            if (target.countryCode in ingressFilteringUcConfig.blockedCountries) {
                errors.reject(
                    "countryCode.blocked",
                    "Your country code is blocked."
                )
            }

            if (target.hosting == null) {
                errors.reject("hosting.null")
            } else {
                if (target.hosting!! && target.org in ingressFilteringUcConfig.blockedDataCenterOrgs) {
                    errors.reject(
                        "hosting.blockedDataCenterOrgs",
                        "Your data-center is blocked."
                    )
                }
            }
        }
    }
}
