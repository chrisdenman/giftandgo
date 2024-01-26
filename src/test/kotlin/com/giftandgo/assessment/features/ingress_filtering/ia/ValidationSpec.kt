package com.giftandgo.assessment.features.ingress_filtering.ia

import com.giftandgo.assessment.PeopleSpeedDataSpecBase
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus.CREATED

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "ip-api", property = "app.features.ingress-filtering.ip-api-url")
)
class ValidationSpec : PeopleSpeedDataSpecBase() {
    @Test
    fun `That malformed UUIDs are reported`() {
        stubIpApi()
        post(ROW__MALFORMED_UUID)
            .expectBody("""{"created":[],"errors":["peopleSpeedData[0].uuid.typeMismatch"]}""")
            .expectStatus(CREATED)
    }

    @Test
    fun `That average speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_AVERAGE)
            .expectBody("""{"created":[],"errors":["peopleSpeedData[0].averageSpeed.DecimalMin"]}""")
            .expectStatus(CREATED)
    }

    @Test
    fun `That top speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_TOP_SPEED)
            .expectBody("""{"created":[],"errors":["peopleSpeedData[0].topSpeed.DecimalMin"]}""")
            .expectStatus(CREATED)
    }

    @Test
    fun `That missing top speeds are caught`() {
        stubIpApi(UUID_X_FORWARDED_FOR__VALUE)
        post(ROW__MISSING_TOP_SPEED)
            .expectBody("""{"created":[],"errors":["peopleSpeedData[0].topSpeed.NotNull"]}""")
            .expectStatus(CREATED)
    }
}
