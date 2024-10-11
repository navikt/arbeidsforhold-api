package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet
import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold

val defaultUtenlandsopphold = Utenlandsopphold(
    startdato = "01.01.2018",
    sluttdato = "01.01.2019",
    land = Kodeverksentitet(
        kode = "land kode",
        beskrivelse = "land beskrivelse",
    ),
    rapporteringsmaaned = "rapporteringsmaaned"
)