package com.giftandgo.assessment.features.ingress_filtering.ia

import com.giftandgo.assessment.PeopleSpeedDataSpecBase
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "ip-api", property = "app.features.ingress-filtering.ip-api-url")
)
@TestPropertySource
class CountryCodeBlockedSpec : PeopleSpeedDataSpecBase() {
    @Test
    fun `That requests with blocked country codes are forbidden`() {
        stubIpApi()
        post()
            .expectBody("""{"created":[],"errors":["Your country is blocked."]}""")
            .expectStatus(FORBIDDEN)
    }
}
