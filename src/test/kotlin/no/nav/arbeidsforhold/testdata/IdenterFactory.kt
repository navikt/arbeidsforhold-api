package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer

object IdenterFactory {

    fun createIdenter() = Identer(type = "Organisasjon")
}
