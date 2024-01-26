package com.giftandgo.assessment.features.ingress_filtering.ui

import com.giftandgo.assessment.features.ingress_filtering.uc.IsIngressPermissibleValidator
import org.springframework.context.MessageSource
import org.springframework.validation.Errors
import java.util.Locale

fun Errors.rejectNull(
    field: String,
    code: String = IsIngressPermissibleValidator.FIELD_ERROR_CODE__FIELD_CAN_NOT_BE_NULL
): Errors =
    also { rejectValue(field, code) }

fun Errors.resolveMessages(messageSource: MessageSource): Iterable<String> =
    resolveObjectMessages(messageSource) + resolveFieldMessages(messageSource)

fun Errors.resolveFieldMessages(messageSource: MessageSource): Iterable<String> =
    fieldErrors
        .map {
            messageSource
                .getMessage(
                    "${it.objectName}.${it.field}.${it.code}",
                    arrayOf(),
                    Locale.getDefault()
                )
        }

fun Errors.resolveObjectMessages(messageSource: MessageSource): Iterable<String> =
    globalErrors
        .map {
            messageSource
                .getMessage(
                    "${it.objectName}.${it.code}",
                    arrayOf(),
                    Locale.getDefault()
                )
        }
