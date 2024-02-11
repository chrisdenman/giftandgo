package com.giftandgo.assessment.person_speed_data_ia

internal data class PeopleCreateResponseData(
    override val created: Iterable<PersonCreateResponse> = emptyList(),
    override val errors: Iterable<String> = emptyList()
) : PeopleCreateResponse
