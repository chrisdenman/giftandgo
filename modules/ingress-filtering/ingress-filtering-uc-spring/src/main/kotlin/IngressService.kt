package com.giftandgo.assessment.ingress_filtering_uc_spring

import com.giftandgo.assessment.ingress_filtering_uc.HostInformation
import com.giftandgo.assessment.ingress_filtering_uc.HostInformationQuery
import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import com.giftandgo.assessment.ingress_filtering_uc.IngressQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.validation.FieldError
import org.springframework.validation.Validator

open class IngressService(private val hostInformationValidator: Validator) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(IngressService::class.java)
    }

    @EventListener
    @Async
    open fun on(hostInformation: HostInformation): IngressDecision = hostInformation.run {
        logger.info("Received $hostInformation")
        ValidationErrors(hostInformation, "ipApi") // @todo change name
            .let { validationErrors ->
                hostInformationValidator.validate(hostInformation, validationErrors)
                IngressDecisionData( // @todo move the logic for correlated events
                    hostInformation.isp,
                    hostInformation.correlationId,
                    validationErrors.allErrors.map {
                        "${it.objectName}.${if (it is FieldError) it.field else ""}.${it.code}"
                    }
                )
            }.also { logger.info("Publishing $it") }
    }

    @EventListener
    @Async
    open fun on(ingressQuery: IngressQuery): HostInformationQuery =
        logger.info("Received $ingressQuery").run {
            HostInformationQueryData(ingressQuery.host, ingressQuery.correlationId)
                .also {
                    logger.info("Publishing $it")
                }
        }

}
