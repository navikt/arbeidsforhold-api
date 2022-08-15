package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold

object UtenlandsOppholdObjectMother {
    val withDummyValues = Utenlandsopphold(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        land = KodeverksentitetObjectMother.withDummyValues,
    )

    val dummyValues = listOf(withDummyValues, Utenlandsopphold())
}
