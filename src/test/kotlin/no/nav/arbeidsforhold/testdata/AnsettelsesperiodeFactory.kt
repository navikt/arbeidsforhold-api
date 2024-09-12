package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet


object AnsettelsesperiodeFactory {

    fun createAnsettelsesperiode() = Ansettelsesperiode(
        startdato = "01.01.2018",
        sluttdato = "01.01.2019",
        varsling = Kodeverksentitet(
            kode = "varsling kode",
            beskrivelse = "varsling beskrivelse"
        ),
        sluttaarsak = Kodeverksentitet(
            kode = "sluttaarsak kode",
            beskrivelse = "sluttaarsak beskrivelse"
        ),
    )
}
