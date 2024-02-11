package com.giftandgo.assessment.ingress_filtering_uc_spring

import org.springframework.validation.AbstractBindingResult
import java.util.Locale

internal class ValidationErrors(private val target: Any, objectName: String) : AbstractBindingResult(objectName) {

    override fun getTarget(): Any = target

    @Throws
    override fun getActualFieldValue(field: String): Any? =
        target::class
            .java
            .getMethod(
                "get" +
                    field
                        .replaceFirstChar {
                            when (it.isLowerCase()) {
                                true -> it.titlecase(Locale.getDefault())
                                false -> it.toString()
                            }
                        }
            )
            .invoke(target)
}
