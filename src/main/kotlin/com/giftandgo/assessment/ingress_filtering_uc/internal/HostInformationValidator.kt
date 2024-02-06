package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import org.springframework.validation.Errors
import org.springframework.validation.Validator

class HostInformationValidator(
    private val blockedCountries: Iterable<String>,
    private val blockedDataCenterOrgs: Iterable<String>
) : Validator {

    companion object {
        const val FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL = "null"
        private const val FIELD__COUNTRY_CODE = "countryCode"
    }

    override fun supports(clazz: Class<*>): Boolean = HostInformation::class.java == clazz // @todo and isAssignableFrom

    override fun validate(target: Any, errors: Errors) {
        errors.run {

            if ((target as HostInformation).countryCode == null) {
                rejectNull(this, FIELD__COUNTRY_CODE)
            } else if (target.countryCode in blockedCountries) {
                rejectValue(FIELD__COUNTRY_CODE, "blocked")
            }

            if (target.org == null) {
                rejectNull(errors, "org")
            } else {
                if (target.hosting == null) {
                    rejectNull(this, "hosting")
                } else {
                    if ((target.hosting == true) && (target.org in blockedDataCenterOrgs)) {
                        rejectValue("org", "blockedDataCenterOrgs")
                    } else {
                    }
                }
            }
        }
    }
}

private fun rejectNull(
    errors: Errors,
    field: String,
    code: String = HostInformationValidator.FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL
): Errors = errors.apply { rejectValue(field, code) }
