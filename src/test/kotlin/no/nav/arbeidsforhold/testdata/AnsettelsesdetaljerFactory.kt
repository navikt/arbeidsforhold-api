package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.testdata.KodeverksentitetFactory.createKodeverksentitet

object AnsettelsesdetaljerFactory {

    fun createAnsettelsesdetaljer() = Ansettelsesdetaljer(
        ansettelsesform = createKodeverksentitet(),
        antallTimerPrUke = 37.5,
        arbeidstidsordning = createKodeverksentitet(),
        avtaltStillingsprosent = 100.0,
        yrke = createKodeverksentitet(),
        sisteStillingsprosentendring = "IFJOR",
        sisteLoennsendring = "IFJOR"
    )
}