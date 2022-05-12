package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesperiode


object AnsettelsesPeriodeObjectMother {

    val withDummyValues = Ansettelsesperiode(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        varsling = KodeverksentitetObjectMother.withDummyValues,
        sluttaarsak = KodeverksentitetObjectMother.withDummyValues,
    )


}
