package no.nav.arbeidsforhold.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class OpplysningspliktigArbeidsgiver (

        val organisasjonsnummer: kotlin.String? = null,
        val type: kotlin.String? = null

)