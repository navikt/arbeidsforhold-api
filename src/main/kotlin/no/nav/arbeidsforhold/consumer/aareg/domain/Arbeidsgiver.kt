package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Arbeidsgiver(

    val organisasjonsnummer: String? = null,
    val offentligIdent: String? = null,
    val type: String? = null

)