package com.giftandgo.assessment.features.ingress_filtering.ia

interface IpApiGateway {
    fun queryBy(host: String): IpApiResponse
}
