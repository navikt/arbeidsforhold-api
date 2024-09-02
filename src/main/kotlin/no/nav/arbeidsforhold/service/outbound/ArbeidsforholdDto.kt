package no.nav.arbeidsforhold.service.outbound

import kotlinx.serialization.Serializable

@Serializable
data class ArbeidsforholdDto(
    val navArbeidsforholdId: Long? = null,
    val eksternArbeidsforholdId: String? = null,
    val type: String? = null,
    val sistBekreftet: String? = null,
    val arbeidsgiver: ArbeidsgiverDto? = null,
    val opplysningspliktigarbeidsgiver: ArbeidsgiverDto? = null,
    val ansettelsesperiode: AnsettelsesperiodeDto? = null,
    val utenlandsopphold: List<UtenlandsoppholdDto>? = null,
    val permisjonPermittering: List<PermisjonPermitteringDto>? = null,
    val arbeidsavtaler: List<ArbeidsavtaleDto>? = null,
    val ansettelsesform: String? = null,
    val antallTimerForTimelonnet: List<AntallTimerForTimeloennetDto>? = null,
    val antallTimerPrUke: Double? = null,
    val arbeidstidsordning: String? = null,
    val sisteStillingsendring: String? = null,
    val sisteLoennsendring: String? = null,
    val stillingsprosent: Double? = null,
    val yrke: String? = null,
    val fartsomraade: String? = null,
    val skipsregister: String? = null,
    val skipstype: String? = null
)
