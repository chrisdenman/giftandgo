package com.giftandgo.assessment.ingress_filtering_uc_spring

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationQuery
import java.util.UUID

internal data class HostInformationQueryData(
    override val host: String,
    override val correlationId: String,
    override val id: String = UUID.randomUUID().toString()
) : HostInformationQuery, CorrelatedApplicationEvent
