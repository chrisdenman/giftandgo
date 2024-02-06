package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationQuery
import java.util.UUID

data class HostInformationQueryData(
    override val host: String,
    override val correlationId: String,
    override val id: String = UUID.randomUUID().toString()
) : HostInformationQuery, CorrelatedApplicationEvent
