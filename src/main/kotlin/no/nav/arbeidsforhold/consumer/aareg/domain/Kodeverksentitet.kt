package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Kodeverksentitet(

    val kode: String,
    val beskrivelse: String

)
