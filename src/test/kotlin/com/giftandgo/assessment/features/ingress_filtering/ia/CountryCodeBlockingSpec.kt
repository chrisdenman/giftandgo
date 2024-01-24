package com.giftandgo.assessment.features.ingress_filtering.ia

import com.giftandgo.assessment.IntegrationSpecBase
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
class CountryCodeBlockingSpec : IntegrationSpecBase() {
    @Test
    fun `That posting people speed data is created successfully`() {
        stubIpApiResponse(uuidXForwardedFor)
        createPeopleSpeedData(
            uuidXForwardedFor,
            "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1"
        )
            .expectBody("""{"errors":["Your country code is blocked."]}""")
            .expectStatus(FORBIDDEN)
    }
}
