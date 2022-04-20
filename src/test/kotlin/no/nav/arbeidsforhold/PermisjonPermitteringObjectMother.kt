package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.consumer.aareg.domain.PermisjonPermittering

object PermisjonPermitteringObjectMother {
    val withDummyValues =
        PermisjonPermittering(periode = PeriodeObjectMother.withDummyValues, prosent = 50.0, type = "Permisjon")

    val dummyValues = listOf(
        withDummyValues,
        PermisjonPermittering(periode = PeriodeObjectMother.withDummyValues, prosent = 50.9, type = "Permisjon")
    )
}
