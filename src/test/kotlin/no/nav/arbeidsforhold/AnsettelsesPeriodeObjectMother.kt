package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Ansettelsesperiode


object AnsettelsesPeriodeObjectMother {

    val withDummyValues = Ansettelsesperiode(
            periode = PeriodeObjectMother.withDummyValues,
            varslingskode = "testkode",
            sluttaarsak = "arbeidstakerHarSagtOppSelv"
    )


}
