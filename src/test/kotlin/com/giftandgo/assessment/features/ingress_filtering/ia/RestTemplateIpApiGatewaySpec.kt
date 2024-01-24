package com.giftandgo.assessment.features.ingress_filtering.ia

import com.giftandgo.assessment.features.ingress_filtering.ui.IngressFilter.Companion.HTTP_HEADER__X_FORWARDED_FOR
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import com.maciejwalkowiak.wiremock.spring.InjectWireMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.http.ResponseEntity
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "ip-api", property = "app.features.ingress-filtering.ip-api-url")
)
class RestTemplateIpApiGatewaySpec {

    @InjectWireMock("ip-api")
    lateinit var wiremock: WireMockServer

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    private val xForwardedFor = UUID.randomUUID().toString()

    private fun stubIpApiResponse(
        xForwardedFor: String,
        status: String = "success",
        countryCode: String = "GB",
        isp: String = "isp",
        org: String = "org",
        hosting: String = "true"
    ) {
        wiremock.stubFor(
            get(urlPathMatching("/json/${xForwardedFor}.*"))
                .willReturn(
                    aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody("""{"status":"$status","countryCode":"$countryCode","isp":"$isp","org":"$org","hosting":$hosting}""")
                )
        )
    }

    fun <T> ResponseEntity<T>.expectStatus(expected: HttpStatus): ResponseEntity<T> {
        assertEquals(expected, statusCode)
        return this
    }

    fun <T> ResponseEntity<T>.expectBody(expected: String): ResponseEntity<T> {
        assertEquals(expected, body)
        return this
    }

    private fun createPeopleSpeedData(
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


    @Test
    fun `That posting people speed data is created successfully`() {
        stubIpApiResponse(xForwardedFor)
        createPeopleSpeedData(
            xForwardedFor,
            "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1"
        )
            .expectBody("""{"created":[{"name":"John Smith","transport":"Rides A Bike","averageSpeed":6.2}],"errors":[]}""")
            .expectStatus(CREATED)
    }

    @Test
    fun `That ip api declinations result in a FORBIDDEN status code`() {
        stubIpApiResponse(xForwardedFor, "failure")
        createPeopleSpeedData(
            xForwardedFor,
            "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1"
        )
            .expectStatus(FORBIDDEN)
    }
}
