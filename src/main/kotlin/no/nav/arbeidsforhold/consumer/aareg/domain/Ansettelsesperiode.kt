package no.nav.arbeidsforhold.consumer.aareg.domain

data class Ansettelsesperiode(

    /* Varslingskode (kodeverk: Varslingskode_5fAa-registeret) - benyttes hvis ansettelsesperiode er lukket maskinelt */
    val sluttaarsak: Kodeverksentitet? = null,
    val varsling: Kodeverksentitet? = null,
    var startdato: String? = null,
    val sluttdato: String? = null

)
