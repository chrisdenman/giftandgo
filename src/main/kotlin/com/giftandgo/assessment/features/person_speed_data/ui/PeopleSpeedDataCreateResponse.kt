package com.giftandgo.assessment.features.person_speed_data.ui

data class PeopleSpeedDataCreateResponse(
    val created: Iterable<PersonSpeedDataCreateResponse> = listOf(),
    val errors: Iterable<String> = listOf()
)
