package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.consumer.aareg.dto.Kodeverksentitet
import no.nav.arbeidsforhold.testdata.RapporteringsmaanederFactory.createRapporteringsmaaneder

object AnsettelsesdetaljerFactory {

    fun createAnsettelsesdetaljer(): Ansettelsesdetaljer {
        val ansettelsesdetaljer = Ansettelsesdetaljer(
            ansettelsesform = Kodeverksentitet(
                kode = "ansettelsesform kode",
                beskrivelse = "ansettelsesform beskrivelse"
            ),
            antallTimerPrUke = 37.5,
            arbeidstidsordning = Kodeverksentitet(
                kode = "arbeidstidsordning kode",
                beskrivelse = "arbeidstidsordning beskrivelse"
            ),
            rapporteringsmaaneder = createRapporteringsmaaneder(),
            avtaltStillingsprosent = 100.0,
            yrke = Kodeverksentitet(
                kode = "yrke kode",
                beskrivelse = "yrke beskrivelse"
            ),
            sisteStillingsprosentendring = "sisteStillingsprosentendring",
            sisteLoennsendring = "sisteLoennsendring",
            fartsomraade = Kodeverksentitet(
                kode = "fartsområde kode",
                beskrivelse = "fartsområde beskrivelse"
            ),
            skipsregister = Kodeverksentitet(
                kode = "skipsregister kode",
                beskrivelse = "skipsregister beskrivelse"
            ),
            fartoeystype = Kodeverksentitet(
                kode = "fartøystype kode",
                beskrivelse = "fartøystype beskrivelse"
            ),
        )
        return ansettelsesdetaljer
    }
}