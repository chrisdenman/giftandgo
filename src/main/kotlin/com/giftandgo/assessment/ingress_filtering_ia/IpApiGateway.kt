package com.giftandgo.assessment.ingress_filtering_ia

interface IpApiGateway {
    fun queryBy(host: String): IpApiResponse
}
