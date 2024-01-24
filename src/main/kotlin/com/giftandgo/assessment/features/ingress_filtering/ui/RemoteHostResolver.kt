package com.giftandgo.assessment.features.ingress_filtering.ui

import jakarta.servlet.http.HttpServletRequest

interface RemoteHostResolver {
    fun resolve(httpServletRequest: HttpServletRequest): String
}
