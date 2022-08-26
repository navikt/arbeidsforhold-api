package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class AntallTimerForTimeloennetDto(
    val antallTimer: String? = null,
    val periode: PeriodeDto? = null,
    val rapporteringsperiode: String? = null
)