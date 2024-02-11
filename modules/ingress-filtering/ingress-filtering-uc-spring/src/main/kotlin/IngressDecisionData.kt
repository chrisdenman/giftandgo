package com.giftandgo.assessment.ingress_filtering_uc_spring

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent
import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import java.util.UUID

internal data class IngressDecisionData(
    override val isp: String?,
    override val correlationId: String,
    override val errorCodes: Iterable<String> = emptyList(),
    override val id: String = UUID.randomUUID().toString(),
) : IngressDecision, CorrelatedApplicationEvent {
    override val isAllowed: Boolean = !errorCodes.iterator().hasNext()
}
