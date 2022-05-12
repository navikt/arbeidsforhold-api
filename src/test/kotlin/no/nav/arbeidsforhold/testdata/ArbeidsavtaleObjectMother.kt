package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesdetaljer

object ArbeidsavtaleObjectMother {

    val withDummyValues = Ansettelsesdetaljer(
        ansettelsesform = KodeverksentitetObjectMother.withDummyValues,
        antallTimerPrUke = 37.5,
        arbeidstidsordning = KodeverksentitetObjectMother.withDummyValues,
        avtaltStillingsprosent = 100.0,
        yrke = KodeverksentitetObjectMother.withDummyValues,
        sisteStillingsprosentendring = "IFJOR",
        sisteLoennsendring = "IFJOR"

    )

    val dummyValues = listOf(withDummyValues, Ansettelsesdetaljer())
}