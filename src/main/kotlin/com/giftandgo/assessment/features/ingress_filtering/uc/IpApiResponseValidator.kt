package com.giftandgo.assessment.features.ingress_filtering.uc

import com.giftandgo.assessment.features.ingress_filtering.ia.IpApiResponse
import com.giftandgo.assessment.features.ingress_filtering.ui.rejectNull
import org.springframework.validation.Errors
import org.springframework.validation.Validator

class IpApiResponseValidator(private val ingressFilteringUcConfig: IngressFilteringUcConfig) : Validator {

    companion object {
        const val FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL = "null"
        private const val FIELD__COUNTRY_CODE = "countryCode"
    }

    override fun supports(clazz: Class<*>): Boolean = IpApiResponse::class.java == clazz

    override fun validate(target: Any, errors: Errors) {
        errors.run {
            if ((target as IpApiResponse).status != "success") {
                errors.rejectValue("status", "unsuccessful")
            } else {
                if (target.countryCode == null) {
                    rejectNull(FIELD__COUNTRY_CODE)
                } else if (target.countryCode in ingressFilteringUcConfig.blockedCountries) {
                    rejectValue(FIELD__COUNTRY_CODE, "blocked")
                }

                if (target.org == null) {
                    rejectNull("org")
                } else {
                    if (target.hosting == null) {
                        rejectNull("hosting")
                    } else {
                        if ((target.hosting == true) && (target.org in ingressFilteringUcConfig.blockedDataCenterOrgs)) {
                            rejectValue("org", "blockedDataCenterOrgs")
                        } else {

                        }
                    }
                }
            }
        }
    }
}
