package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.testdata.KodeverksentitetFactory.createKodeverksentitet


object AnsettelsesperiodeFactory {

    fun createAnsettelsesperiode() = Ansettelsesperiode(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        varsling = createKodeverksentitet(),
        sluttaarsak = createKodeverksentitet(),
    )
}
