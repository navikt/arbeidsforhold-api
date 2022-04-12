package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Arbeidsavtale(
    val ansettelsesform: String? = null,
    /* Antall timer per uke */
    val antallTimerPrUke: Double? = null,
    /* Arbeidstidsordning (kodeverk: Arbeidstidsordninger) */
    val arbeidstidsordning: String? = null,
    /* Avl&oslash;nningstype (kodeverk: Avloenningstyper) */
    val avloenningstype: String? = null,
    /* Beregnet antall timer per uke */
    val beregnetAntallTimerPrUke: Double? = null,
    /* Beregnet stillingsprosent */
    val beregnetStillingsprosent: Double? = null,
    val bruksperiode: Bruksperiode? = null,
    val gyldighetsperiode: Gyldighetsperiode? = null,
    val sistLoennsendring: String? = null,
    val sistStillingsendring: String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Stillingsprosent */
    val stillingsprosent: Double? = null,
    /* Yrke (kodeverk: Yrker) */
    val yrke: String? = null,
    val fartsomraade: String? = null,
    val skipsregister: String? = null,
    val skipstype: String? = null
)