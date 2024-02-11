package com.giftandgo.assessment.person_speed_data_ia

import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
interface PersonSpeedDataController {

    @ResponseBody
    @RequestMapping("/peopleSpeedData")
    @PostMapping(
        consumes = [TEXT_PLAIN_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(CREATED)
    fun createPeople(
        @RequestBody command: List<String>,
        response: HttpServletResponse
    ): PeopleCreateResponse
}
