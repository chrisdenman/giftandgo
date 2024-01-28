package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.CREATED

class RestTemplateIpApiGatewayIntSpec : ApiSpecBase() {

    @Test
    fun `That posting people speed data is created successfully`() {
        stubIpApi()
        post()
            .hasJsonBody("""{"created":[{"name":"John Smith","transport":"Rides A Bike","averageSpeed":6.2}],"errors":[]}""")
            .expectStatus(CREATED)
    }
}
