package com.giftandgo.assessment.ingress_filtering_uc_spring

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app.features.ingress-filtering")
data class IngressFilteringUcSpringProperties @ConstructorBinding constructor(
    val blockedCountries: List<String>,
    val blockedDataCenterOrgs: List<String>
)
