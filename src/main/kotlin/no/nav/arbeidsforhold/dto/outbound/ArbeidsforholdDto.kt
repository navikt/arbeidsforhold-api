package no.nav.arbeidsforhold.dto.outbound

data class ArbeidsforholdDto (

        val navarbeidsforholdId: Long? = null,
        val type: String? = null,
        val sistBekreftet : String? = null,
        val arbeidsgiver : ArbeidsgiverDto? = null,
        val opplysningspliktigarbeidsgiver : OpplysningspliktigArbeidsgiverDto? = null,
        val ansettelsesPeriode : PeriodeDto? = null,
        val utenlandsopphold: ArrayList<UtenlandsoppholdDto>? = null,
        val permisjonPermittering: ArrayList<PermisjonPermitteringDto>? = null,
        val arbeidsavtaler: ArrayList<ArbeidsavtaleDto>? = null,

        val antallTimerPrUke: Double? = null,
        var arbeidstidsOrdning: String? = null,
        val sisteStillingsEndring: String? = null,
        val sisteLoennsEndring: String? = null,
        val stillingsProsent: Double? = null,
        var yrke: String? = null
)