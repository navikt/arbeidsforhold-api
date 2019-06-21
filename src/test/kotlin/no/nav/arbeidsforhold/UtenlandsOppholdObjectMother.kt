package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Utenlandsopphold

object UtenlandsOppholdObjectMother {
    val withDummyValues = Utenlandsopphold(
            periode = PeriodeObjectMother.withDummyValues,
            landkode = "NOR"
    )


    val arrayOfDummyValues = arrayOf(
            UtenlandsOppholdObjectMother.withDummyValues,
            Utenlandsopphold(

            )
    )
}