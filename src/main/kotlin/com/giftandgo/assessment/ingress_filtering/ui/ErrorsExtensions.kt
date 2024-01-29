package com.giftandgo.assessment.ingress_filtering.ui

import com.giftandgo.assessment.ingress_filtering.uc.IpApiResponseValidator
import org.springframework.context.MessageSource
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import java.util.Locale

fun Errors.rejectNull(
    field: String,
    code: String = IpApiResponseValidator.FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL
): Errors =
    also { rejectValue(field, code) }

fun Errors.resolveMessages(
    messageSource: MessageSource,
    locale: Locale = Locale.getDefault()
): Iterable<String> =
    allErrors.map {
        messageSource.getMessage(
            "${it.objectName}.${if (it is FieldError) it.field else ""}.${it.code}",
            it.arguments,
            locale
        )
    }
