package com.giftandgo.assessment.person_speed_data.ui

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.AbstractHttpMessageConverter

class TextPlainToStringListConverter : AbstractHttpMessageConverter<List<String>>(MediaType.TEXT_PLAIN) {

    override fun supports(clazz: Class<*>) = clazz.name == List::class.java.name

    override fun readInternal(clazz: Class<out List<String>>, inputMessage: HttpInputMessage): List<String> =
        inputMessage
            .body
            .bufferedReader()
            .use { it.readLines() }

    override fun writeInternal(t: List<String>, outputMessage: HttpOutputMessage) = TODO()
}
