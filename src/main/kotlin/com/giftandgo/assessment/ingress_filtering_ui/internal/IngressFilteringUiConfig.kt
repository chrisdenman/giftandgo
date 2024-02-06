package com.giftandgo.assessment.ingress_filtering_ui.internal

import com.giftandgo.assessment.ingress_filtering_uc.IngressDecision
import jakarta.servlet.Filter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import java.util.concurrent.Executors

@ConditionalOnProperty(name = ["app.features.ingress-filtering"], havingValue = "true")
@Configuration
class IngressFilteringUiConfig {

    @Bean
    fun publisher(
        applicationEventPublisher: ApplicationEventPublisher,
        ingressFilteringUiProperties: IngressFilteringUiProperties
    ): AsyncRequestResponsePublisher<IngressQueryEvent, IngressDecision> =
        AsyncRequestResponsePublisher<IngressQueryEvent, IngressDecision>(
            applicationEventPublisher,
            Executors.newSingleThreadExecutor(),
            ingressFilteringUiProperties.decisionTimeOutMilliSeconds
        )

    @Bean
    fun ingressFilter(
        messageSource: MessageSource,
        publisher: AsyncRequestResponsePublisher<IngressQueryEvent, IngressDecision>
    ): Filter = IngressFilter(messageSource, publisher)

    @Bean
    fun ingressFilterRegistration(ingressFilter: Filter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>().apply {
            filter = ingressFilter
            order = Ordered.HIGHEST_PRECEDENCE + 1000
            setName("IP Address Filter")
            addUrlPatterns("/peopleSpeedData") // @todo
        }
}
