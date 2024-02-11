package com.giftandgo.assessment

import org.junit.jupiter.api.Test

class ValidationIntSpec : WireMockIntSpecBase() {
    @Test
    fun `That malformed UUIDs are reported`() {
        stubIpApi()
        post(ROW__MALFORMED_UUID)
            .withJson(errorsOnlyJson("peopleSpeedData[0].uuid.typeMismatch"))
            .isCreated()
    }

    @Test
    fun `That average speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_AVERAGE)
            .withJson(errorsOnlyJson("peopleSpeedData[0].averageSpeed.DecimalMin"))
            .isCreated()
    }

    @Test
    fun `That top speeds cannot be negatively valued`() {
        stubIpApi()
        post(ROW__NEGATIVE_TOP_SPEED)
            .withJson(errorsOnlyJson("peopleSpeedData[0].topSpeed.DecimalMin"))
            .isCreated()
    }

    @Test
    fun `That missing top speeds are caught`() {
        stubIpApi(UUID_X_FORWARDED_FOR__VALUE)
        post(ROW__MISSING_TOP_SPEED)
            .withJson(errorsOnlyJson("peopleSpeedData[0].topSpeed.NotNull"))
            .isCreated()
    }
}
