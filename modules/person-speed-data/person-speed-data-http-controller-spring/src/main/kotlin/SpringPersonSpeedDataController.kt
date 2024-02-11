package com.giftandgo.assessment.person_speed_data_ia

import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.ResolvableType.forClass
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.DataBinder
import org.springframework.validation.DataBinder.ValueResolver
import org.springframework.validation.FieldError
import org.springframework.validation.Validator
import java.util.StringTokenizer

internal class SpringPersonSpeedDataController(private val springValidator: Validator) : PersonSpeedDataController {
    override fun createPeople(
        command: List<String>,
        response: HttpServletResponse
    ): PeopleCreateResponse =
        command
            .foldIndexed(
                listOf<BindingResult>() to listOf<PersonCreateCommand>()
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
                            false -> acc.first to (acc.second + br.target as PersonCreateCommand)
                        }
                    }
            }.run {
                when (first.isEmpty()) {
                    true -> PeopleCreateResponseData(
                        second.map {
                            PersonCreateResponseData(it.name!!, it.transport!!, it.averageSpeed!!)
                        }
                    )

                    false -> {
                        response.status = HttpStatus.BAD_REQUEST.value()
                        PeopleCreateResponseData(
                            errors = first.fold(emptyList()) { acc, curr ->
                                acc + curr.allErrors.map {
                                    "${it.objectName}.${if (it is FieldError) it.field else ""}.${it.code}"
                                }
                            }
                        )
                    }
                }
            }

    private fun bind(textualPropertyValues: Map<String, String?>, objectName: String): DataBinder =
        DataBinder(null, objectName).apply {
            setTargetType(forClass(PersonCreateCommand::class.java))
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
