package com.giftandgo.assessment

import org.junit.jupiter.api.Test

class IpApiRestTemplateGatewayIntSpec : WireMockIntSpecBase() {

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
