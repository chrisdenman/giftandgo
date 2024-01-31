package com.giftandgo.assessment.ingress_filtering_uc.internal

import com.giftandgo.assessment.ingress_filtering_uc.HostInformationGateway
import com.giftandgo.assessment.ingress_filtering_ia.ipapi.HostInformationData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.validation.SimpleErrors
import org.springframework.validation.Validator
import java.util.UUID

class IngressServiceSpec {

    private val host = UUID.randomUUID().toString()
    private val status = UUID.randomUUID().toString()
    private val countryCode = UUID.randomUUID().toString()
    private val hosting = true
    private val org = UUID.randomUUID().toString()
    private val isp = UUID.randomUUID().toString()
    private val ipApiResponse =
        HostInformationData(status, countryCode, hosting, org, isp)
    private val errors = SimpleErrors(ipApiResponse)

    @Test
    fun ingressServiceReturnsTheCorrectValuesOnSuccess() {
        val hostInformationGatewayMock = mock<HostInformationGateway> {
            on { queryBy(host) }.thenReturn(ipApiResponse)
        }
        val validatorMock = mock<Validator> {
            on { validateObject(ipApiResponse) }.thenReturn(errors)
        }
        val subject = IngressService(hostInformationGatewayMock, validatorMock)
        val ingressDecision = subject.getDecisionFor(host)

        assertFalse(ingressDecision.errors.hasErrors())
        assertEquals(isp, ingressDecision.ipProvider)
    }
}
