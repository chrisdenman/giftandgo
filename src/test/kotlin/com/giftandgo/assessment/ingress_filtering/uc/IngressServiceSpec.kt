package com.giftandgo.assessment.ingress_filtering.uc

import com.giftandgo.assessment.ingress_filtering.ia.IpApiGateway
import com.giftandgo.assessment.ingress_filtering.ia.IpApiResponseData
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
        IpApiResponseData(status, countryCode, hosting, org, isp)
    private val errors = SimpleErrors(ipApiResponse)

    @Test
    fun ingressServiceReturnsTheCorrectValuesOnSuccess() {
        val ipApiGatewayMock = mock<IpApiGateway> {
            on { queryBy(host) }.thenReturn(ipApiResponse)
        }
        val validatorMock = mock<Validator> {
            on { validateObject(ipApiResponse) }.thenReturn(errors)
        }
        val subject = IngressService(ipApiGatewayMock, validatorMock)
        val ingressDecision = subject.getIngressDecisionFor(host)

        assertFalse(ingressDecision.errors.hasErrors())
        assertEquals(isp, ingressDecision.ipProvider)
    }
}
