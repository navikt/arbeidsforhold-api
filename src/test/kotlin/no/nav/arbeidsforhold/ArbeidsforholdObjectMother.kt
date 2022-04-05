package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Arbeidsforhold


object ArbeidsforholdObjectMother {
    val withDummyValues = Arbeidsforhold(
            ansettelsesperiode = AnsettelsesPeriodeObjectMother.withDummyValues,
            antallTimerForTimeloennet = AntallTimerForTimeloennetObjectMother.dummyValues,
            arbeidsavtaler = ArbeidsavtaleObjectMother.dummyValues,
            navArbeidsforholdId = 1111,
            arbeidsforholdId = "1111",
            arbeidsgiver = ArbeidsgiverObjectMother.withDummyValues,
            innrapportertEtterAOrdningen = true,
            permisjonPermitteringer = PermisjonPermitteringObjectMother.dummyValues,
            sistBekreftet = "01.01.2016",
            type = "Organisasjon",
            utenlandsopphold = UtenlandsOppholdObjectMother.dummyValues
    )

}