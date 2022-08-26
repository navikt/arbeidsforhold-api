package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class PeriodeDto(
    val periodeFra: String? = null,
    val periodeTil: String? = null
)