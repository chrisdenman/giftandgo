package com.giftandgo.assessment.features.person_speed_data.ui

import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.ResolvableType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.validation.BindingResult
import org.springframework.validation.DataBinder
import org.springframework.validation.FieldError
import org.springframework.validation.Validator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.*
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.StringTokenizer

@RestController
@RequestMapping("/peopleSpeedData")
class PersonSpeedDataController(private val springValidator: Validator) {
    @PostMapping(
        consumes = [TEXT_PLAIN_VALUE],
        produces = [APPLICATION_JSON_VALUE]
    )
    @ResponseStatus(CREATED)
    fun createPeople(
        @RequestBody command: List<String>,
        response: HttpServletResponse
    ): PeopleSpeedDataCreateResponse =
        command
            .foldIndexed(listOf<Pair<Int, BindingResult>>() to listOf<PersonSpeedDataCreateCommand>()) { recordIndex, acc, personLine ->
                StringTokenizer(personLine, "|")
                    .run {
                        val textualPropertyValues: Map<String, String?> =
                            listOf("uuid", "id", "name", "likes", "transport", "averageSpeed", "topSpeed")
                                .associateWith { if (hasMoreTokens()) nextToken() else null }
                        validate(textualPropertyValues).run {
                            when (bindingResult.hasErrors()) {
                                true -> (acc.first + (recordIndex to bindingResult)) to acc.second
                                false -> acc.first to (acc.second + target as PersonSpeedDataCreateCommand)
                            }
                        }
                    }
            }.let { it ->
                if (it.first.isEmpty()) {
                    PeopleSpeedDataCreateResponse(
                        it.second.map {
                            PersonSpeedDataCreateResponse(it.name, it.transport, it.averageSpeed);
                        }
                    )
                } else {
                    response.status = HttpStatus.BAD_REQUEST.value()
                    PeopleSpeedDataCreateResponse(
                        errors = it.first.fold(listOf()) { acc, curr ->
                            acc + curr.second.allErrors.map { "peopleSpeedData[${curr.first}].${(it as FieldError).field}.${it.code!!}" }
                        })
                }
            }

    private fun validate(textualPropertyValues: Map<String, String?>): DataBinder =
        DataBinder(null).apply {
            setTargetType(ResolvableType.forClass(PersonSpeedDataCreateCommand::class.java))
            construct(
                object : DataBinder.ValueResolver {
                    override fun resolveValue(name: String, type: Class<*>): Any? =
                        textualPropertyValues[name]

                    override fun getNames(): MutableSet<String> = textualPropertyValues.keys.toMutableSet()
                }
            )
            validator = springValidator
            if (target != null) {
                validate()
            }
        }
}
