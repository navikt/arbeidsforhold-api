package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class ArbeidsavtaleDto(
    val ansettelsesform: String? = null,
    val antallTimerPrUke: Double? = null,
    val arbeidstidsordning: String? = null,
    val sisteStillingsendring: String? = null,
    val sisteLoennsendring: String? = null,
    val yrke: String? = null,
    val gyldighetsperiode: PeriodeDto? = null,
    val stillingsprosent: Double? = null,
    val fartsomraade: String? = null,
    val skipsregister: String? = null,
    val skipstype: String? = null
)