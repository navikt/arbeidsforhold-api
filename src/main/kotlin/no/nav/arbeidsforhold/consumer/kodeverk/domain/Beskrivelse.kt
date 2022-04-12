package no.nav.arbeidsforhold.consumer.kodeverk.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Beskrivelse {
    val term: String? = null
}