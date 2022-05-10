package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class Utenlandsopphold(

    val startdato: String? = null,
    val sluttdato: String? = null,
    /* Landkode (kodeverk: Landkoder) */
    val land: Kodeverksentitet? = null,
    /* Rapporteringsperiode for utenlandsopphold, format (ISO-8601): yyyy-MM */
    val rapporteringsmaaned: String? = null,
)