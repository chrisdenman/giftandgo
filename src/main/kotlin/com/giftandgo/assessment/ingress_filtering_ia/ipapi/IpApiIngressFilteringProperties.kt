package com.giftandgo.assessment.ingress_filtering_ia.ipapi;

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import java.net.URI

@ConfigurationProperties(prefix = "app.features.ingress-filtering.ip-api")
data class IpApiIngressFilteringProperties @ConstructorBinding constructor(
    val url: URI
)
