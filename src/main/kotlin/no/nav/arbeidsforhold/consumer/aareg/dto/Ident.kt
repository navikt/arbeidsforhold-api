package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class Ident(
    val ident: String? = null,
    val type: String? = null,
)