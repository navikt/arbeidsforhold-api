package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsforhold


object ArbeidsforholdObjectMother {
    val withDummyValues = Arbeidsforhold(
        ansettelsesperiode = AnsettelsesPeriodeObjectMother.withDummyValues,
        timerMedTimeloenn = AntallTimerForTimeloennetObjectMother.dummyValues,
        ansettelsesdetaljer = ArbeidsavtaleObjectMother.dummyValues,
        navArbeidsforholdId = 1111,
        id = "1111",
        arbeidssted = ArbeidsgiverObjectMother.withDummyValues,
        permisjoner = PermisjonPermitteringObjectMother.dummyValues,
        permitteringer = PermisjonPermitteringObjectMother.dummyValues,
        sistBekreftet = "01.01.2016",
        type = KodeverksentitetObjectMother.withDummyValues,
        utenlandsopphold = UtenlandsOppholdObjectMother.dummyValues
    )

}