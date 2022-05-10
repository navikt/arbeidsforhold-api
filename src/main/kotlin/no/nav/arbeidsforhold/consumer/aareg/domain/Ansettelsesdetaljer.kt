package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ansettelsesdetaljer(
    val ansettelsesform: String? = null,
    /* Antall timer per uke */
    val antallTimerPrUke: Double? = null,
    /* Arbeidstidsordning (kodeverk: Arbeidstidsordninger) */
    val arbeidstidsordning: String? = null,
    val rapporteringsmaaneder: Rapporteringsmaaneder? = null,
    val sisteLoennsendring: String? = null,
    val sisteStillingsprosentendring: String? = null,
    /* Stillingsprosent */
    val avtaltStillingsprosent: Double? = null,
    /* Yrke (kodeverk: Yrker) */
    val yrke: String? = null,
    val fartsomraade: String? = null,
    val skipsregister: String? = null,
    val fartoeystype: String? = null
)