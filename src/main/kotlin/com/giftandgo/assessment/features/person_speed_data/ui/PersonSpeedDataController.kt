package com.giftandgo.assessment.features.person_speed_data.ui

import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.ResolvableType.forClass
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.validation.BindingResult
import org.springframework.validation.DataBinder
import org.springframework.validation.DataBinder.ValueResolver
import org.springframework.validation.FieldError
import org.springframework.validation.Validator
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
            .foldIndexed(
                listOf<BindingResult>() to listOf<PersonSpeedDataCreateCommand>()
            ) { recordIndex, acc, personLine ->
                StringTokenizer(personLine, "|")
                    .run {
                        val br = bind(
                            listOf(
                                "uuid",
                                "id",
                                "name",
                                "likes",
                                "transport",
                                "averageSpeed",
                                "topSpeed"
                            ).associateWith { if (hasMoreTokens()) nextToken() else null },
                            "peopleSpeedData[$recordIndex]"
                        )
                            .bindingResult

                        when (br.hasErrors()) {
                            true -> (acc.first + br) to acc.second
                            false -> acc.first to (acc.second + br.target as PersonSpeedDataCreateCommand)
                        }
                    }
            }.let { it ->
                if (it.first.isEmpty()) {
                    PeopleSpeedDataCreateResponse(
                        it.second.map {
                            PersonSpeedDataCreateResponse(it.name!!, it.transport!!, it.averageSpeed!!)
                        }
                    )
                } else {
                    response.status = HttpStatus.BAD_REQUEST.value()
                    PeopleSpeedDataCreateResponse(
                        errors = it.first.fold(emptyList()) { acc, curr ->
                            acc + curr.allErrors.map {
                                "${it.objectName}.${if (it is FieldError) it.field else ""}.${it.code}"
                            }
                        }
                    )
                }
            }

    private fun bind(textualPropertyValues: Map<String, String?>, objectName: String): DataBinder =
        DataBinder(null, objectName).apply {
            setTargetType(forClass(PersonSpeedDataCreateCommand::class.java))
            construct(
                object : ValueResolver {
                    override fun resolveValue(name: String, type: Class<*>): Any? =
                        textualPropertyValues[name]

                    override fun getNames(): Set<String> = textualPropertyValues.keys
                }
            )
            validator = springValidator

            if (target != null) {
                validate()
            }
        }
}
