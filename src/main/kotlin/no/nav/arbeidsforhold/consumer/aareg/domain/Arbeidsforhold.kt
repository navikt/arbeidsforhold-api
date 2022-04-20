package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Arbeidsforhold(

    val ansettelsesperiode: Ansettelsesperiode? = null,
    /* Antall timer med timel&oslash;nn */
    val antallTimerForTimeloennet: List<AntallTimerForTimeloennet>? = null,
    /* Arbeidsavtaler - gjeldende og evt. med historikk */
    val arbeidsavtaler: List<Arbeidsavtale>? = null,
    /* Arbeidsforhold-id fra opplysningspliktig */
    val arbeidsforholdId: String? = null,
    val arbeidsgiver: Arbeidsgiver? = null,
    val arbeidstaker: Person? = null,
    /* Er arbeidsforholdet innrapportert via A-Ordningen? */
    val innrapportertEtterAOrdningen: Boolean? = null,
    /* Arbeidsforhold-id i AAREG */
    val navArbeidsforholdId: Long? = null,
    val opplysningspliktig: Arbeidsgiver? = null,
    /* Permisjoner og/eller permitteringer */
    val permisjonPermitteringer: List<PermisjonPermittering>? = null,
    val registrert: String? = null,
    /* Tidspunkt for siste bekreftelse av arbeidsforhold, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val sistBekreftet: String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null,
    /* Arbeidsforholdtype (kodeverk: Arbeidsforholdtyper) */
    val type: String? = null,
    /* Utenlandsopphold */
    val utenlandsopphold: List<Utenlandsopphold>? = null
)