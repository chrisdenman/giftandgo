package com.giftandgo.assessment.person_speed_data_ia

import java.math.BigDecimal

interface PersonCreateResponse {
    val name: String
    val transport: String
    val averageSpeed: BigDecimal
}
