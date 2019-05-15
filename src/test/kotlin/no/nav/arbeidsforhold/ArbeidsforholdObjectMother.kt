package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Arbeidsforhold


object ArbeidsforholdObjectMother {
    val withDummyValues = Arbeidsforhold(
            ansettelsesperiode = AnsettelsesPeriodeObjectMother.withDummyValues,
            antallTimerForTimeloennet = AntallTimerForTimeloennetObjectMother.arrayOfDummyValues,
            arbeidsavtaler = ArbeidsavtaleObjectMother.arrayOfDummyValues,
            arbeidsforholdId = "1111",
            arbeidsgiver = ArbeidsgiverObjectMother.withDummyValues,
            innrapportertEtterAOrdningen = true,
            permisjonPermitteringer = PermisjonPermitteringObjectMother.arrayOfDummyValues,
            sistBekreftet = "01.01.2016",
            type = "Organisasjon",
            utenlandsopphold = UtenlandsOppholdObjectMother.arrayOfDummyValues
    )

}