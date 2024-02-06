package com.giftandgo.assessment.ingress_filtering_ui.internal

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app.features.ingress-filtering")
@Suppress("ConfigurationProperties")
data class IngressFilteringUiProperties @ConstructorBinding constructor(
    val decisionTimeOutMilliSeconds: Long
)
