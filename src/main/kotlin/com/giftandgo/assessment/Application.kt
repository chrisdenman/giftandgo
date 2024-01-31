package com.giftandgo.assessment

import com.giftandgo.assessment.ingress_filtering_ia.ipapi.IpApiIngressFilteringConfig
import com.giftandgo.assessment.ingress_filtering_uc.internal.IngressFilteringUcConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@Suppress("RedundantUnitReturnType")
fun main(args: Array<String>): Unit {
    runApplication<Application>(*args)
}

@EnableConfigurationProperties(IpApiIngressFilteringConfig::class, IngressFilteringUcConfig::class)
@SpringBootApplication
class Application
