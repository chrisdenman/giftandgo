package com.giftandgo.assessment.features.service_history.ia

import jakarta.annotation.Nullable
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import java.net.InetAddress
import java.net.URI
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "SERVICE_HISTORY")
data class ServiceHistoryEntity(

    @NotNull
    @Convert(converter = UriAttributeConverter::class)
    @Column(length = 1024)
    @Size(min = 0, max = 1024)
    override val requestUri: URI,

    @NotNull
    override val receivedAt: LocalDateTime,

    @NotNull
    @Convert(converter = HttpStatusAttributeConverter::class)
    @Min(100)
    override val statusCode: HttpStatus,

    @NotNull
    @Convert(converter = InetAddressAttributeConverter::class)
    @Size(min = 7, max = 256)
    @Column(length = 256)
    override val requestIpAddress: InetAddress,

    @NotNull
    @Size(min = 2, max = 2)
    @Column(length = 2)
    override val requestCountryCode: String,

    @Nullable
    @Size(min = 1, max = 128)
    @Column(length = 128)
    override val ipProvider: String?,

    @NotNull
    @Size(min = 1)
    @Convert(converter = DurationAttributeConverter::class)
    override val servicingDurationMs: Duration,

    @NotNull
    @Size(min = 36, max = 36)
    @Column(length = 36)
    @field:Id override val id: String = UUID.randomUUID().toString(),
) : ServiceHistory

@Converter
private class UriAttributeConverter : AttributeConverter<URI, String> {
    override fun convertToDatabaseColumn(attribute: URI): String = attribute.toString()
    override fun convertToEntityAttribute(dbData: String): URI = URI.create(dbData)
}

@Converter
private class HttpStatusAttributeConverter : AttributeConverter<HttpStatus, Int> {
    override fun convertToDatabaseColumn(attribute: HttpStatus): Int = attribute.value()
    override fun convertToEntityAttribute(dbData: Int): HttpStatus = TODO()
}

@Converter
private class InetAddressAttributeConverter : AttributeConverter<InetAddress, String> {
    override fun convertToDatabaseColumn(attribute: InetAddress): String = attribute.hostAddress
    override fun convertToEntityAttribute(dbData: String): InetAddress = TODO()
}

@Converter
private class DurationAttributeConverter : AttributeConverter<Duration, Long> {
    override fun convertToDatabaseColumn(attribute: Duration): Long = attribute.toMillis()
    override fun convertToEntityAttribute(dbData: Long): Duration = Duration.ofMillis(dbData)
}
