package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TimerMedTimeloenn(

    val antall: Double? = null,
    val startdato: String? = null,
    val sluttdato: String? = null,
    val rapporteringsmaaned: String? = null,
)