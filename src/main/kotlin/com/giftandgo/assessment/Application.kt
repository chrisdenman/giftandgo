package com.giftandgo.assessment

import com.giftandgo.assessment.ingress_filtering.uc.IngressFilteringUcConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@EnableConfigurationProperties(
    com.giftandgo.assessment.ingress_filtering.ia.IngressFilteringIaConfig::class,
    IngressFilteringUcConfig::class
)
@SpringBootApplication
class DemoApplication
