package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.PermisjonPermittering
import no.nav.arbeidsforhold.testdata.KodeverksentitetFactory.createKodeverksentitet

object PermisjonPermitteringFactory {

    fun createPermisjonPermittering() = PermisjonPermittering(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        prosent = 50.0,
        type = createKodeverksentitet(),
    )
}
