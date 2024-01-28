package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.FORBIDDEN

class IpApiNullResponseFieldsIntSpec : ApiSpecBase() {
    @Test
    fun `That if only a successful status message is included, the request is forbidden`() {
        stubIpApi(
            body = """{"status":"success"}"""
        )
        post()
            .expectStatus(FORBIDDEN)
    }

    @Test
    fun `That if only a successful status message and org field are included, the request is forbidden`() {
        stubIpApi(
            body = """{"status":"success", "org": "org"}"""
        )
        post()
            .expectStatus(FORBIDDEN)
    }

    @Test
    fun `That if only an unsuccessful status message field is included, the request is forbidden`() {
        stubIpApi(
            body = """{"status":"not_a_success"}"""
        )
        post()
            .expectStatus(FORBIDDEN)
    }
}
