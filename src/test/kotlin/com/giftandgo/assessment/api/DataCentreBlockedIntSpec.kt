package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.test.context.TestPropertySource

@TestPropertySource
class DataCentreBlockedIntSpec : WireMockIntSpecBase() {
    @Test
    fun `That requests with blocked data centres that are hosting are forbidden`() {
        stubIpApi()
        post()
            .withJson(errorsOnlyJson("Your data centre is blocked."))
            .isForbidden()
    }
}
