package com.giftandgo.assessment.ingress_filtering.uc

import com.giftandgo.assessment.ingress_filtering.ia.IpApiGateway
import org.springframework.validation.AbstractBindingResult
import org.springframework.validation.Validator
import java.util.Locale

class IngressService(
    private val ipApiGateway: IpApiGateway,
    private val validator: Validator,
) {
    fun getIngressDecisionFor(host: String): IngressDecision = ipApiGateway
        .queryBy(host)
        .let { response ->
            ValidationErrors(response, "ipApi")
                .let { validationErrors ->
                    validator.validate(response, validationErrors)
                    IngressDecisionData(response.isp, validationErrors)
                }
        }
}

private class ValidationErrors(private val target: Any, objectName: String) : AbstractBindingResult(objectName) {

    override fun getTarget(): Any = target

    @Throws
    override fun getActualFieldValue(field: String): Any? =
        target::class
            .java
            .getMethod("get" + field.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            .invoke(target)
}
