package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Rapporteringsmaaneder(

    /* yyyy-MM */
    val fra: String? = null,
    /* yyyy-MM */
    val til: String? = null
)