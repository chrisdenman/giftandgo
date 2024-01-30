package com.giftandgo.assessment.ingress_filtering_ui

import jakarta.servlet.http.HttpServletRequest

interface RemoteHostResolver {
    fun resolve(httpServletRequest: HttpServletRequest): String
}
