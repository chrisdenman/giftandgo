package com.giftandgo.assessment.person_speed_data_ui.internal

import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes

import org.springframework.web.context.request.WebRequest

class CustomErrorAttributes : DefaultErrorAttributes() {

    override fun getErrorAttributes(webRequest: WebRequest, options: ErrorAttributeOptions?): Map<String, Any> =
        super.getErrorAttributes(webRequest, options).apply {
            remove("trace")
            remove("message")
        }
}
