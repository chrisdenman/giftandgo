package com.giftandgo.assessment.person_speed_data_ia

interface PeopleCreateResponse {
    val created: Iterable<PersonCreateResponse>
    val errors: Iterable<String>
}
