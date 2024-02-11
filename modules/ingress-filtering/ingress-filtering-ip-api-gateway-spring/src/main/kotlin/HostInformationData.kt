package com.giftandgo.assessment.ingress_filtering.ip_api

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import java.util.UUID.randomUUID

internal data class HostInformationData(
    override val hosting: Boolean?,
    override val org: String?,
    override val isp: String?,
    override val countryCode: String?,
    override val correlationId: String = randomUUID().toString(),
    override val id: String = randomUUID().toString(),
) : HostInformation, CorrelatedApplicationEvent
