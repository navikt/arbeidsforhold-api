package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.AntallTimerForTimeloennet

object AntallTimerForTimeloennetObjectMother {
    val withDummyValues = AntallTimerForTimeloennet(
        antallTimer = 37.5,
        periode = PeriodeObjectMother.withDummyValues,
        rapporteringsperiode = "MAI"
    )

    val dummyValues = listOf(withDummyValues, AntallTimerForTimeloennet())
}