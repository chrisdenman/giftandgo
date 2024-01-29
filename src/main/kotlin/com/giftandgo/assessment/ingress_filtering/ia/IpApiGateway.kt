package com.giftandgo.assessment.ingress_filtering.ia

interface IpApiGateway {
    fun queryBy(host: String): IpApiResponse
}
