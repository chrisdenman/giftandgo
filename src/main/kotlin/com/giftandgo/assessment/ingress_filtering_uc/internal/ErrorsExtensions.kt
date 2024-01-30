package com.giftandgo.assessment.ingress_filtering_uc.internal

import org.springframework.validation.Errors

fun Errors.rejectNull(
    field: String,
    code: String = IpApiResponseValidator.FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL
): Errors =
    also { rejectValue(field, code) }
