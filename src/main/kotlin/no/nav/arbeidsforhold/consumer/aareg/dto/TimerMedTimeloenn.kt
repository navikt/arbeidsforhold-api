package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class TimerMedTimeloenn(
    val antall: Double? = null,
    val startdato: String? = null,
    val sluttdato: String? = null,
    val rapporteringsmaaned: String? = null,
)