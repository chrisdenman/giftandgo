package com.giftandgo.assessment.person_speed_data.ui

data class PeopleSpeedDataCreateResponse(
    val created: Iterable<PersonSpeedDataCreateResponse> = emptyList(),
    val errors: Iterable<String> = emptyList()
)
