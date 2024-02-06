package com.giftandgo.assessment.ingress_filtering_ia.internal

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import java.util.UUID

data class HostInformationData(
    override val hosting: Boolean?,
    override val org: String?,
    override val isp: String?,
    override val countryCode: String?,
    override val correlationId: String = UUID.randomUUID().toString(),
    override val id: String = UUID.randomUUID().toString(),
) : HostInformation, CorrelatedApplicationEvent
