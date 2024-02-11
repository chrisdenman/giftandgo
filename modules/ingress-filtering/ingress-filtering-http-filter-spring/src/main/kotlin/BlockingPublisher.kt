package com.giftandgo.assessment.ingress_filtering.http_filter_spring

import com.giftandgo.assessment.ingress_filtering_uc.CorrelatedApplicationEvent

interface BlockingPublisher<S : CorrelatedApplicationEvent, R : CorrelatedApplicationEvent> {
    fun publishAndBlockFor(event: S, responseType: Class<R>): R
}
