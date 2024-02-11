package com.giftandgo.assessment.ingress_filtering.http_filter_spring

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app.features.ingress-filtering")
data class IngressFilteringUiProperties @ConstructorBinding constructor(
    val decisionTimeOutMilliSeconds: Long
)
