package com.giftandgo.assessment.person_speed_data_ia

import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.Validator

@Configuration
class PersonSpeedDataBeans {

    @Bean
    fun customHttpMessageConverters(): HttpMessageConverters =
        HttpMessageConverters(TextPlainToStringListConverter())

    @Bean
    fun personSpeedDataController(validator: Validator): PersonSpeedDataController =
        SpringPersonSpeedDataController(validator)

    @Bean
    fun resourceControllerErrorAttributes(): ErrorAttributes =
        CustomErrorAttributes()
}
