package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet

fun createArbeidsforhold() = Arbeidsforhold(
    ansettelsesperiode = createAnsettelsesperiode(),
    timerMedTimeloenn = listOf(createTimerMedTimeloenn()),
    ansettelsesdetaljer = listOf(createAnsettelsesdetaljer()),
    id = "1111",
    arbeidssted = createIdenterArbeidssted(),
    arbeidstaker = createIdenterArbeidstaker(),
    navArbeidsforholdId = 1111,
    opplysningspliktig = createIdenterOpplysningspliktig(),
    permisjoner = listOf(createPermisjonPermittering()),
    permitteringer = listOf(createPermisjonPermittering()),
    sistBekreftet = "01.01.2016",
    type = Kodeverksentitet(
        kode = "type kode",
        beskrivelse = "type beskrivelse"
    ),
    utenlandsopphold = listOf(createUtenlandsopphold())
)