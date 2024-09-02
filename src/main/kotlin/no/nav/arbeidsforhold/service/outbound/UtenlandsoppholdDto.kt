package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandsoppholdDto(
    val periode: PeriodeDto? = null,
    val rapporteringsperiode: String? = null,
    val land: String? = null
)