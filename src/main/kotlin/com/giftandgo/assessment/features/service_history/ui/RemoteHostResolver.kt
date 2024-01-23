package com.giftandgo.assessment.features.service_history.ui

import jakarta.servlet.http.HttpServletRequest

interface RemoteHostResolver {
    fun resolve(httpServletRequest: HttpServletRequest): String
}
