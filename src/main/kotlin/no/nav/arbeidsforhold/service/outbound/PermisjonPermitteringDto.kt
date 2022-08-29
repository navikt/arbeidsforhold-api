package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class PermisjonPermitteringDto(
    var periode: PeriodeDto? = null,
    var type: String? = null,
    var prosent: String? = null
)