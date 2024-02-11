package com.giftandgo.assessment.ingress_filtering.http_filter_spring

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
class IngressFilteringUiBeans {

    @Bean
    fun publisher(
        applicationEventPublisher: ApplicationEventPublisher,
        ingressFilteringUiProperties: IngressFilteringUiProperties
    ): BlockingPublisher<IngressQueryEvent, IngressDecision> =
        AsyncRequestResponsePublisher(
            applicationEventPublisher,
            Executors.newSingleThreadExecutor(),
            ingressFilteringUiProperties.decisionTimeOutMilliSeconds
        )

    @Bean
    fun ingressFilter(
        messageSource: MessageSource,
        publisher: BlockingPublisher<IngressQueryEvent, IngressDecision>
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
