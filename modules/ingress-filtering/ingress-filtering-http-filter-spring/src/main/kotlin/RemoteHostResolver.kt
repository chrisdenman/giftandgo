package com.giftandgo.assessment.ingress_filtering.http_filter_spring

import jakarta.servlet.http.HttpServletRequest

interface RemoteHostResolver {
    fun resolve(httpServletRequest: HttpServletRequest): String
}
