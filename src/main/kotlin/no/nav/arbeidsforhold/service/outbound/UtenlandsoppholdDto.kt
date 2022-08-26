package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class UtenlandsoppholdDto(
    var periode: PeriodeDto? = null,
    var rapporteringsperiode: String? = null,
    var land: String? = null
)