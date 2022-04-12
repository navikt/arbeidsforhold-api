package no.nav.arbeidsforhold.consumer.aareg.domain


data class Ansettelsesperiode(

    val bruksperiode: Bruksperiode? = null,
    val periode: Periode? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Varslingskode (kodeverk: Varslingskode_5fAa-registeret) - benyttes hvis ansettelsesperiode er lukket maskinelt */
    val varslingskode: String? = null,
    val sluttaarsak: String? = null
)