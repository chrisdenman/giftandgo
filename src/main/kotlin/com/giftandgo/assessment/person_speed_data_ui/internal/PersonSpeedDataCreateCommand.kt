package com.giftandgo.assessment.person_speed_data_ui.internal

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class PersonSpeedDataCreateCommand(

    @field:NotNull
    val uuid: UUID?,

    @field:NotNull
    @field:NotBlank
    val id: String?,

    @field:NotNull
    @field:NotBlank
    val name: String?,

    @field:NotNull
    @field:NotBlank
    val likes: String?,

    @field:NotNull
    @field:NotBlank
    val transport: String?,

    @field:DecimalMin("0.0")
    @field:NotNull
    val averageSpeed: BigDecimal?,

    @field:DecimalMin("0.0")
    @field:NotNull
    val topSpeed: BigDecimal?
)
