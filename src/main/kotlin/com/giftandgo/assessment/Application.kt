package com.giftandgo.assessment

import com.giftandgo.assessment.ingress_filtering_ia.internal.IngressFilteringIaConfig
import com.giftandgo.assessment.ingress_filtering_uc.internal.IngressFilteringUcConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@Suppress("RedundantUnitReturnType")
fun main(args: Array<String>): Unit {
    runApplication<Application>(*args)
}

@EnableConfigurationProperties(IngressFilteringIaConfig::class, IngressFilteringUcConfig::class)
@SpringBootApplication
class Application
