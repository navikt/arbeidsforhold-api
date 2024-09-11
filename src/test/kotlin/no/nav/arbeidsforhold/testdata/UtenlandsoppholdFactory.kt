package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold
import no.nav.arbeidsforhold.testdata.KodeverksentitetFactory.createKodeverksentitet

object UtenlandsoppholdFactory {

    fun createUtenlandsopphold() = Utenlandsopphold(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        land = createKodeverksentitet(),
    )
}
