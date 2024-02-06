package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.IngressQuery
import java.util.UUID

data class IngressQueryEvent(
    override val host: String,
    override val id: String = UUID.randomUUID().toString(),
    override val correlationId: String = UUID.randomUUID().toString(),
) : IngressQuery, CorrelatedApplicationEvent
