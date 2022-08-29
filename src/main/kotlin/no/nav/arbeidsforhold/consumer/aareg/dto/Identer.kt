package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class Identer(
    val identer: List<Ident>? = null,
    val type: String? = null
)