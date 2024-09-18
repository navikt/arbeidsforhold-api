package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet

val defaultArbeidsforhold = Arbeidsforhold(
    ansettelsesperiode = defaultAnsettelsesperiode,
    timerMedTimeloenn = listOf(defaultTimerMedTimeloenn),
    ansettelsesdetaljer = listOf(defaultAnsettelsesdetaljer),
    id = "1111",
    arbeidssted = defaultIdenterArbeidssted,
    arbeidstaker = defaultIdenterArbeidstaker,
    navArbeidsforholdId = 1111,
    opplysningspliktig = defaultIdenterOpplysningspliktig,
    permisjoner = listOf(defaultPermisjonPermittering),
    permitteringer = listOf(defaultPermisjonPermittering),
    sistBekreftet = "01.01.2016",
    type = Kodeverksentitet(
        kode = "type kode",
        beskrivelse = "type beskrivelse"
    ),
    utenlandsopphold = listOf(defaultUtenlandsopphold)
)