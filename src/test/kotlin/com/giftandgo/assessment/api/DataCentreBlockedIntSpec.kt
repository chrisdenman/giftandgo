package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.test.context.TestPropertySource

@TestPropertySource
class DataCentreBlockedIntSpec : ApiSpecBase() {
    @Test
    fun `That requests with blocked data centres that are hosting are forbidden`() {
        stubIpApi()
        post()
            .hasJsonBody(errorsOnlyJson("Your data centre is blocked."))
            .expectStatus(FORBIDDEN)
    }
}
