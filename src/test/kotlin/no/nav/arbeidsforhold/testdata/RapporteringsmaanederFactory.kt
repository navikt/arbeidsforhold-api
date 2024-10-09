package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Rapporteringsmaaneder

object RapporteringsmaanederFactory {

    fun createRapporteringsmaaneder() = Rapporteringsmaaneder(
        fra = "2019-11",
        til = null,
    )
}