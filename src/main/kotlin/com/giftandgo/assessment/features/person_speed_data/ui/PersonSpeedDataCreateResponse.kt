package com.giftandgo.assessment.features.person_speed_data.ui

import java.math.BigDecimal

data class PersonSpeedDataCreateResponse(
    val name: String,
    val transport: String,
    val averageSpeed: BigDecimal
)
