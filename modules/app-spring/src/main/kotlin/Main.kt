package com.giftandgo.assessment

import com.giftandgo.assessment.ingress_filtering.http_filter_spring.IngressFilteringUiBeans
import com.giftandgo.assessment.ingress_filtering.http_filter_spring.IngressFilteringUiProperties
import com.giftandgo.assessment.ingress_filtering.ip_api.IpApiBeans
import com.giftandgo.assessment.ingress_filtering.ip_api.SpringIpApiProperties
import com.giftandgo.assessment.ingress_filtering_uc_spring.IngressFilteringUcSpringBeans
import com.giftandgo.assessment.ingress_filtering_uc_spring.IngressFilteringUcSpringProperties
import com.giftandgo.assessment.person_speed_data_ia.PersonSpeedDataBeans
import com.giftandgo.assessment.service_history_http_filter_spring.ServiceHistoryUiConfig
import com.giftandgo.assessment.service_history_uc_spring.ServiceHistoryUcBeans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Suppress("RedundantUnitReturnType")
fun main(args: Array<String>): Unit {
    runApplication<Main>(*args)
}

@EnableConfigurationProperties(
    IngressFilteringUcSpringProperties::class,
    IngressFilteringUiProperties::class,
    SpringIpApiProperties::class,
)
@SpringBootApplication
@Import(
    IngressFilteringUcSpringBeans::class,
    IngressFilteringUiBeans::class,
    IpApiBeans::class,
    PersonSpeedDataBeans::class,
    ServiceHistoryUcBeans::class,
    ServiceHistoryUiConfig::class,
)
class Main
