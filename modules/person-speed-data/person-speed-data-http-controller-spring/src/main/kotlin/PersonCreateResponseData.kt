package com.giftandgo.assessment.person_speed_data_ia

import java.math.BigDecimal

internal data class PersonCreateResponseData(
    override val name: String,
    override val transport: String,
    override val averageSpeed: BigDecimal
) : PersonCreateResponse
