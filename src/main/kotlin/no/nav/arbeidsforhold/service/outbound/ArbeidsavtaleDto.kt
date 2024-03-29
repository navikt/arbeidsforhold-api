package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class ArbeidsavtaleDto(
    val ansettelsesform: String? = null,
    val antallTimerPrUke: Double? = null,
    var arbeidstidsordning: String? = null,
    val sisteStillingsendring: String? = null,
    val sisteLoennsendring: String? = null,
    var yrke: String? = null,
    val gyldighetsperiode: PeriodeDto? = null,
    val stillingsprosent: Double? = null,
    var fartsomraade: String? = null,
    var skipsregister: String? = null,
    var skipstype: String? = null
)