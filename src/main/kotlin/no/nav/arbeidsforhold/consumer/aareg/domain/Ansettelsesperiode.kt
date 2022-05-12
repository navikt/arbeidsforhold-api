package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ansettelsesperiode(

    /* Varslingskode (kodeverk: Varslingskode_5fAa-registeret) - benyttes hvis ansettelsesperiode er lukket maskinelt */
    val sluttaarsak: Kodeverksentitet? = null,
    val varsling: Kodeverksentitet? = null,
    var startdato: String? = null,
    val sluttdato: String? = null

)
