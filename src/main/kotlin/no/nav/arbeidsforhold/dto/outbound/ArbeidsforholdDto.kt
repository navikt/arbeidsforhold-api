package no.nav.arbeidsforhold.dto.outbound

data class ArbeidsforholdDto (

        val navArbeidsforholdId: Long? = null,
        val eksternArbeidsforholdId: String? = null,
        var type: String? = null,
        val sistBekreftet : String? = null,
        val arbeidsgiver : ArbeidsgiverDto? = null,
        val opplysningspliktigarbeidsgiver : OpplysningspliktigArbeidsgiverDto? = null,
        val ansettelsesperiode : AnsettelsesperiodeDto? = null,
        val utenlandsopphold: ArrayList<UtenlandsoppholdDto>? = null,
        val permisjonPermittering: ArrayList<PermisjonPermitteringDto>? = null,
        val arbeidsavtaler: ArrayList<ArbeidsavtaleDto>? = null,
        val ansettelsesform: String? = null,
        val antallTimerForTimelonnet: ArrayList<AntallTimerForTimeloennetDto>? = null,
        val antallTimerPrUke: Double? = null,
        var arbeidstidsordning: String? = null,
        val sisteStillingsendring: String? = null,
        val sisteLoennsendring: String? = null,
        val stillingsprosent: Double? = null,
        var yrke: String? = null,
        var fartsomraade: String? = null,
        var skipsregister: String? = null,
        var skipstype: String? = null

)
