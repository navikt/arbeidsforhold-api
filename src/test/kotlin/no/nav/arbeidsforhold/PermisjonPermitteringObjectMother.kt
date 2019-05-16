package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.PermisjonPermittering

object PermisjonPermitteringObjectMother {
    val withDummyValues = PermisjonPermittering(
            periode = PeriodeObjectMother.withDummyValues,
            prosent = 50.0,
            type = "Permisjon"

    )

    val arrayOfDummyValues = arrayOf(
            PermisjonPermitteringObjectMother.withDummyValues,
            PermisjonPermittering(

            )
    )
}
