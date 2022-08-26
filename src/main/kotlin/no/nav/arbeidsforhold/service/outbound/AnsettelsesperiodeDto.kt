package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class AnsettelsesperiodeDto(
    val periode: PeriodeDto? = null,
    val varslingskode: String? = null,
    var sluttaarsak: String? = null
)
