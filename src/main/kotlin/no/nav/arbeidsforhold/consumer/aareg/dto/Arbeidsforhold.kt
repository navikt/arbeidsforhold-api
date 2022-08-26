package no.nav.arbeidsforhold.consumer.aareg.dto

import kotlinx.serialization.Serializable

@Serializable
data class Arbeidsforhold(
    val ansettelsesperiode: Ansettelsesperiode? = null,
    /* Antall timer med timel&oslash;nn */
    val timerMedTimeloenn: List<TimerMedTimeloenn>? = null,
    /* Arbeidsavtaler - gjeldende og evt. med historikk */
    val ansettelsesdetaljer: List<Ansettelsesdetaljer>? = null,
    /* Arbeidsforhold-id fra opplysningspliktig */
    val id: String? = null,
    val arbeidssted: Identer? = null,
    val arbeidstaker: Identer? = null,
    /* Arbeidsforhold-id i AAREG */
    val navArbeidsforholdId: Long? = null,
    val opplysningspliktig: Identer? = null,
    val permisjoner: List<PermisjonPermittering>? = null,
    val permitteringer: List<PermisjonPermittering>? = null,
    /* Tidspunkt for siste bekreftelse av arbeidsforhold, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val sistBekreftet: String? = null,
    /* Arbeidsforholdtype (kodeverk: Arbeidsforholdtyper) */
    val type: Kodeverksentitet? = null,
    /* Utenlandsopphold */
    val utenlandsopphold: List<Utenlandsopphold>? = null
)