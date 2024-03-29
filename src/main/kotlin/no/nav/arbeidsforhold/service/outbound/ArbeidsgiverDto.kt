package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
class ArbeidsgiverDto(
    val orgnr: String? = null,
    val fnr: String? = null,
    val type: String? = null,
    val orgnavn: String? = null
)