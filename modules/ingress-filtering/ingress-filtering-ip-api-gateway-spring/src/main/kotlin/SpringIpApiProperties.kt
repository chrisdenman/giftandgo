package com.giftandgo.assessment.ingress_filtering.ip_api

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import java.net.URI

@ConfigurationProperties(prefix = "app.features.ingress-filtering.ip-api")
data class SpringIpApiProperties @ConstructorBinding constructor(
    override val url: URI
) : IpApiProperties
