package no.nav.arbeidsforhold.dto.outbound

data class ArbeidsavtaleDto (

        val antallTimerPrUke: Double? = null,
        val arbeidstidsordning: String? = null,
        val sisteStillingsendring : String? = null,
        val sisteLoennsendring : String? = null,
        val yrke : String? = null,
        val gyldighetsperiode: PeriodeDto? = null,
        val stillingsprosent: Double? = null,
        val fartsomraade : String? = null,
        val skipsregister: String? = null,
        val skipstype: String? = null
)