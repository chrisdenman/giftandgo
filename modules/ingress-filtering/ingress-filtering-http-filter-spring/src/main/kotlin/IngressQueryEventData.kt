package com.giftandgo.assessment.ingress_filtering.http_filter_spring

import java.util.UUID.randomUUID

internal data class IngressQueryEventData(
    override val host: String,
    override val id: String = randomUUID().toString(),
    override val correlationId: String = randomUUID().toString(),
) : IngressQueryEvent
