package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class PermisjonPermitteringDto(
    val periode: PeriodeDto? = null,
    val type: String? = null,
    val prosent: String? = null
)