package com.giftandgo.assessment.ingress_filtering_uc.internal

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app.features.ingress-filtering")
@Suppress("ConfigurationProperties")
data class IngressFilteringUcProperties @ConstructorBinding constructor(
    val blockedCountries: List<String>,
    val blockedDataCenterOrgs: List<String>
)
