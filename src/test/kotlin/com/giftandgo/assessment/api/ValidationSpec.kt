package com.giftandgo.assessment.api

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus.CREATED

class ValidationSpec : ApiSpecBase() {
    @Test
    fun `That malformed UUIDs are reported`() {
        stubIpApi()
        post(ROW__MALFORMED_UUID)
            .hasJsonBody(errorsOnlyJson("peopleSpeedData[0].uuid.typeMismatch"))
            .expectStatus(CREATED)
    }

    @Test
    fun `That average speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_AVERAGE)
            .hasJsonBody(errorsOnlyJson("peopleSpeedData[0].averageSpeed.DecimalMin"))
            .expectStatus(CREATED)
    }

    @Test
    fun `That top speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_TOP_SPEED)
            .hasJsonBody(errorsOnlyJson("peopleSpeedData[0].topSpeed.DecimalMin"))
            .expectStatus(CREATED)
    }

    @Test
    fun `That missing top speeds are caught`() {
        stubIpApi(UUID_X_FORWARDED_FOR__VALUE)
        post(ROW__MISSING_TOP_SPEED)
            .hasJsonBody(errorsOnlyJson("peopleSpeedData[0].topSpeed.NotNull"))
            .expectStatus(CREATED)
    }
}
