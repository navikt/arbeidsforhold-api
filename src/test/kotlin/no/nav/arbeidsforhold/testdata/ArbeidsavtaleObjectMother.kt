package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesdetaljer

object ArbeidsavtaleObjectMother {

    val withDummyValues = Ansettelsesdetaljer(
        ansettelsesform = "fast",
        antallTimerPrUke = 37.5,
        arbeidstidsordning = "Fast",
        avtaltStillingsprosent = 100.0,
        yrke = "YRK",
        sisteStillingsprosentendring = "IFJOR",
        sisteLoennsendring = "IFJOR"

    )

    val dummyValues = listOf(withDummyValues, Ansettelsesdetaljer())
}