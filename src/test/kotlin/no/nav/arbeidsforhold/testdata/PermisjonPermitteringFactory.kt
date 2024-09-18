package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet
import no.nav.arbeidsforhold.consumer.aareg.dto.PermisjonPermittering

fun createPermisjonPermittering() = PermisjonPermittering(
    startdato = "01.01.2018",
    sluttdato = "01.01.2019",
    prosent = 50.0,
    type = Kodeverksentitet(
        kode = "type kode",
        beskrivelse = "type beskrivelse"
    ),
)