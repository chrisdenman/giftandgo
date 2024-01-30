package com.giftandgo.assessment.ingress_filtering_uc.internal

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.validation.SimpleErrors
import java.util.UUID

class IngressDecisionDataSpec {
    @Test
    fun ingressIsAllowedIfEmptyErrorsPresent() {
        assertTrue(
            IngressDecisionData(
                SimpleErrors(this),
                UUID.randomUUID().toString()
            )
                .isAllowed
        )
    }

    @Test
    fun ingressNotAllowedIfErrorsPresent() {
        assertFalse(
            IngressDecisionData(
                SimpleErrors(this).apply { reject("") },
                UUID.randomUUID().toString()
            )
                .isAllowed
        )
    }
}
