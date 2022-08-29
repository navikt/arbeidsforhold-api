package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class Kodeverksentitet(
    val kode: String,
    val beskrivelse: String
)
