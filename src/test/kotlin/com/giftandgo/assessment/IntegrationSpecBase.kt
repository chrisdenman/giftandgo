package com.giftandgo.assessment

import com.giftandgo.assessment.features.ingress_filtering.ui.IngressFilter.Companion.HTTP_HEADER__X_FORWARDED_FOR
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import com.maciejwalkowiak.wiremock.spring.InjectWireMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.http.ResponseEntity
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "ip-api", property = "app.features.ingress-filtering.ip-api-url")
)
class IntegrationSpecBase {

    @InjectWireMock("ip-api")
    protected lateinit var wiremock: WireMockServer

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @Suppress("unused")
    @Autowired
    protected lateinit var environment: Environment

    protected val uuidXForwardedFor = UUID.randomUUID().toString()

    fun stubIpApiResponse(
        xForwardedFor: String,
        status: String = "success",
        countryCode: String = "GB",
        isp: String = "isp",
        org: String = "org",
        hosting: String = "true"
    ) {
        wiremock.stubFor(
            WireMock.get(WireMock.urlPathMatching("/json/${xForwardedFor}.*"))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""{"status":"$status","countryCode":"$countryCode","isp":"$isp","org":"$org","hosting":$hosting}""")
                )
        )
    }

    fun <T> ResponseEntity<T>.expectStatus(expected: HttpStatus): ResponseEntity<T> =
        this.apply { assertEquals(expected, statusCode) }

    fun <T> ResponseEntity<T>.expectBody(expected: String): ResponseEntity<T> =
        this.apply {
            assertEquals(expected, body)
        }

    fun createPeopleSpeedData(
        xForwardedFor: String,
        requestContent: String,
        headersMutator: (HttpHeaders).() -> HttpHeaders = { this }
    ): ResponseEntity<String> =
        restTemplate.postForEntity(
            "/peopleSpeedData",
            HttpEntity(
                requestContent,
                headersMutator(
                    HttpHeaders().apply {
                        contentType = TEXT_PLAIN
                        accept = listOf(APPLICATION_JSON)
                        add(HTTP_HEADER__X_FORWARDED_FOR, xForwardedFor)
                    }
                )
            ),
            String::class.java)
}
