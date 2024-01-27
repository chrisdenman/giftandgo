package com.giftandgo.assessment.features.ingress_filtering.ia

import com.giftandgo.assessment.PeopleSpeedDataSpecBase
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.FORBIDDEN

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "ip-api", property = "app.features.ingress-filtering.ip-api-url")
)
class RestTemplateIpApiGatewaySpec : PeopleSpeedDataSpecBase() {

    @Test
    fun `That posting people speed data is created successfully`() {
        stubIpApi()
        post()
            .hasBody(
                """{"created":[{"name":"John Smith","transport":"Rides A Bike","averageSpeed":6.2}],"errors":[]}"""
            )
            .expectStatus(CREATED)
    }

    @Test
    fun `That ip api declinations result in a FORBIDDEN status code`() {
        stubIpApi(status = "not_success")
        post()
            .expectStatus(FORBIDDEN)
    }
}
