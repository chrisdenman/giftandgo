package com.giftandgo.assessment

import com.giftandgo.assessment.ingress_filtering_ia.ipapi.IpApiIngressFilteringProperties
import com.giftandgo.assessment.ingress_filtering_uc.internal.IngressFilteringUcProperties
import com.giftandgo.assessment.ingress_filtering_ui.internal.IngressFilteringUiProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@Suppress("RedundantUnitReturnType")
fun main(args: Array<String>): Unit {
    runApplication<Application>(*args)
}

@EnableConfigurationProperties(
    IpApiIngressFilteringProperties::class,
    IngressFilteringUcProperties::class,
    IngressFilteringUiProperties::class
)
@SpringBootApplication
class Application
