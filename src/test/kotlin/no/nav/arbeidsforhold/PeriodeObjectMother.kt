package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.consumer.aareg.domain.Periode

object PeriodeObjectMother {

    val withDummyValues = Periode(
        fom = "01.01.2018",
        tom = "01.01.2019"
    )

}
