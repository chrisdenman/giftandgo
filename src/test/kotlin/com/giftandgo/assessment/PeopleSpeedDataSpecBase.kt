package com.giftandgo.assessment

import com.fasterxml.jackson.databind.ObjectMapper
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
class PeopleSpeedDataSpecBase {

    companion object {
        private fun createRow(
            uuid: String = "18148426-89e1-11ee-b9d1-0242ac120002",
            id: String = "1X1D14",
            name: String = "John Smith",
            likes: String = "Likes Apricots",
            transport: String = "Rides A Bike",
            avgSpeed: String = "6.2",
            topSpeed: String = "12.1",
        ): String =
            listOf(uuid, id, name, likes, transport, avgSpeed, topSpeed)
                .joinToString("|")

        val ROW = createRow()

        val ROW__NEGATIVE_AVERAGE = createRow(avgSpeed = "-6.2")
        val ROW__NEGATIVE_TOP_SPEED = createRow(topSpeed = "-12.1")
        val ROW__MISSING_TOP_SPEED = createRow(topSpeed = "")
        val ROW__MALFORMED_UUID = createRow("_not_a_uuid_")

        val UUID_X_FORWARDED_FOR__VALUE = UUID.randomUUID().toString()

        fun errorsOnlyJson(vararg errorCodes: String): String =
            """{"created":[],"errors":[${errorCodes.joinToString(",", "\"", "\"")}]}"""

        const val DEFAULT__STATUS = "success"
        const val DEFAULT__COUNTRY_CODE =   "GB"
        const val DEFAULT__ISP =    "isp"
        const val DEFAULT__ORG =    "org"
        const val DEFAULT__HOSTING =    "true"
    }

    @InjectWireMock("ip-api")
    protected lateinit var wiremock: WireMockServer

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @Suppress("unused")
    @Autowired
    protected lateinit var environment: Environment

    protected val jsonMapper: ObjectMapper = ObjectMapper()

    fun stubIpApi(
        xForwardedFor: String = UUID_X_FORWARDED_FOR__VALUE,
        status: String = DEFAULT__STATUS,
        countryCode: String = DEFAULT__COUNTRY_CODE,
        isp: String = DEFAULT__ISP,
        org: String = DEFAULT__ORG,
        hosting: String = DEFAULT__HOSTING
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
        apply { assertEquals(expected, statusCode) }


    fun <T> ResponseEntity<T>.hasJsonBody(expectedJson: String): ResponseEntity<T> =
        apply {
            assertEquals(
                jsonMapper.readTree(expectedJson),
                jsonMapper.readTree(body.toString())
            )
        }

    fun post(
        requestContent: String = ROW,
        xForwardedFor: String = UUID_X_FORWARDED_FOR__VALUE,
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
            String::class.java
        )
}
