package com.giftandgo.assessment

import com.giftandgo.assessment.features.ingress_filtering.ia.IngressFilteringIaConfig
import com.giftandgo.assessment.features.ingress_filtering.uc.IngressFilteringUcConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@EnableConfigurationProperties(
    IngressFilteringIaConfig::class,
    IngressFilteringUcConfig::class
)
@SpringBootApplication
class DemoApplication
