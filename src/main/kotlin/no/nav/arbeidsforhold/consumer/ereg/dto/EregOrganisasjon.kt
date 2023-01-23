package no.nav.arbeidsforhold.consumer.ereg.dto

import kotlinx.serialization.Serializable

@Serializable
data class EregOrganisasjon(
    val navn: Navn
)

@Serializable
data class Navn(
    val sammensattnavn: String? = null,
)