package com.giftandgo.assessment.features.ingress_filtering.uc

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.SimpleErrors
import java.util.UUID

class IngressDecisionDataSpec {
    @Test
    fun ingressIsAllowedIfNoErrorsPresent() {
        assertTrue(
            IngressDecisionData(UUID.randomUUID().toString(), SimpleErrors(this))
                .isAllowed
        )
    }

    @Test
    fun ingressNotAllowedIfNoErrorsPresent() {
        assertFalse(
            IngressDecisionData(
                UUID.randomUUID().toString(),
                SimpleErrors(this).apply { reject("") }
            )
                .isAllowed
        )
    }
}
