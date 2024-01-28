package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.test.context.TestPropertySource

@TestPropertySource
class CountryCodeBlockedIntSpec : ApiSpecBase() {
    @Test
    fun `That requests with blocked country codes are forbidden`() {
        stubIpApi()
        post()
            .hasJsonBody(errorsOnlyJson("Your country is blocked."))
            .expectStatus(FORBIDDEN)
    }
}
