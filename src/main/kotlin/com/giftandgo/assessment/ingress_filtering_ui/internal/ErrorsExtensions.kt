package com.giftandgo.assessment.ingress_filtering_ui.internal

import org.springframework.context.MessageSource
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import java.util.Locale

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
