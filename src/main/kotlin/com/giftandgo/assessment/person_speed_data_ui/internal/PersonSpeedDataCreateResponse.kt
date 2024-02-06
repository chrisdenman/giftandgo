package com.giftandgo.assessment.person_speed_data_ui.internal

import java.math.BigDecimal

data class PersonSpeedDataCreateResponse(
    val name: String,
    val transport: String,
    val averageSpeed: BigDecimal
)
