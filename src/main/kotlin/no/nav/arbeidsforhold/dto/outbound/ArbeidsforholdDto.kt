package no.nav.arbeidsforhold.dto.outbound

data class ArbeidsforholdDto (

        val navArbeidsforholdId: Long? = null,
        var type: String? = null,
        val sistBekreftet : String? = null,
        val arbeidsgiver : ArbeidsgiverDto? = null,
        val opplysningspliktigarbeidsgiver : OpplysningspliktigArbeidsgiverDto? = null,
        val ansettelsesPeriode : PeriodeDto? = null,
        val utenlandsopphold: ArrayList<UtenlandsoppholdDto> = ArrayList<UtenlandsoppholdDto>(),
        val permisjonPermittering: ArrayList<PermisjonPermitteringDto>? = null,
        val arbeidsavtaler: ArrayList<ArbeidsavtaleDto>? = null,

        val antallTimerPrUke: Double? = null,
        var arbeidstidsOrdning: String? = null,
        val sisteStillingsEndring: String? = null,
        val sisteLoennsEndring: String? = null,
        val stillingsProsent: Double? = null,
        var yrke: String? = null,
        var fartsomraade: String? = null,
        var skipsregister: String? = null,
        var skipstype: String? = null

)