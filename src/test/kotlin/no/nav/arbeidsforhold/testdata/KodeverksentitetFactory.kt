package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet


object KodeverksentitetFactory {

    fun createKodeverksentitet() = Kodeverksentitet(
        kode = "dummy kode",
        beskrivelse = "dummy beskrivelse"
    )
}
