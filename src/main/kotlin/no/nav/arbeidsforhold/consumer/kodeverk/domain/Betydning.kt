package no.nav.arbeidsforhold.consumer.kodeverk.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Betydning {
    var beskrivelser: Map<String, Beskrivelse>? = null
        get() {
            if (field == null) {
                field = HashMap()
            }
            return field
        }
        private set
}