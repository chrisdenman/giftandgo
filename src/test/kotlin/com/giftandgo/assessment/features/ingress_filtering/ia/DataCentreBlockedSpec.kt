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
class DataCentreBlockedSpec : PeopleSpeedDataSpecBase() {
    @Test
    fun `That requests with blocked data centres that are hosting are forbidden`() {
        stubIpApi()
        post()
            .hasJsonBody("""{"created":[],"errors":["Your data centre is blocked."]}""")
            .expectStatus(FORBIDDEN)
    }
}
