package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.domain.PermisjonPermittering

object PermisjonPermitteringObjectMother {
    val withDummyValues =
        PermisjonPermittering(
            startdato = "01.01.2018",
            sluttdato = "01.01.2019",
            prosent = 50.0,
            type = KodeverksentitetObjectMother.withDummyValues,
        )

    val dummyValues = listOf(
        withDummyValues,
        PermisjonPermittering(
            startdato = "01.01.2018",
            sluttdato = "01.01.2019",
            prosent = 50.9,
            type = KodeverksentitetObjectMother.withDummyValues,
        )
    )
}
