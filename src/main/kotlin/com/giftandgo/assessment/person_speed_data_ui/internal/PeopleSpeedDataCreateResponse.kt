package com.giftandgo.assessment.person_speed_data_ui.internal

data class PeopleSpeedDataCreateResponse(
    val created: Iterable<PersonSpeedDataCreateResponse> = emptyList(),
    val errors: Iterable<String> = emptyList()
)
