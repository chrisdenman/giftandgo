package com.giftandgo.assessment.person_speed_data_ui.internal

import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator

@Configuration
class PersonSpeedDataUiConfig {

    @Bean
    fun customHttpMessageConverters(): HttpMessageConverters = HttpMessageConverters(TextPlainToStringListConverter())

    @Bean
    fun personSpeedDataController(validator: Validator): PersonSpeedDataController =
        PersonSpeedDataController(validator)

    @Bean
    fun resourceControllerErrorAttributes(): ErrorAttributes = CustomErrorAttributes()
}
