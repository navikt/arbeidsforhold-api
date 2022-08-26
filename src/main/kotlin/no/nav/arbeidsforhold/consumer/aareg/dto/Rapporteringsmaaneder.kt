package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class Rapporteringsmaaneder(
    /* yyyy-MM */
    val fra: String? = null,
    /* yyyy-MM */
    val til: String? = null
)