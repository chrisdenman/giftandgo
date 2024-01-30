package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test

class RestTemplateIpApiGatewayIntSpec : WireMockIntSpecBase() {

    @Test
    fun `That posting people speed data is created successfully`() {
        stubIpApi()
        post()
            .withJson(
                """{"created":[{"name":"John Smith","transport":"Rides A Bike","averageSpeed":6.2}],"errors":[]}"""
            )
            .isCreated()
    }
}
