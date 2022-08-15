package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn

object AntallTimerForTimeloennetObjectMother {
    val withDummyValues = TimerMedTimeloenn(
        antall = 37.5,
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        rapporteringsmaaned = "MAI"
    )

    val dummyValues = listOf(withDummyValues, TimerMedTimeloenn())
}