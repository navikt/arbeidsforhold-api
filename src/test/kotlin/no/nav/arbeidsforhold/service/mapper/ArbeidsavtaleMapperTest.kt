package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.testdata.AnsettelsesdetaljerFactory.createAnsettelsesdetaljer
import org.junit.jupiter.api.Test

class ArbeidsavtaleMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Ansettelsesdetaljer = createAnsettelsesdetaljer()
        val outbound: ArbeidsavtaleDto = inbound.toOutbound()

        assertSoftly(outbound) {
            ansettelsesform shouldBe "ansettelsesform beskrivelse"
            antallTimerPrUke shouldBe 37.5
            arbeidstidsordning shouldBe "arbeidstidsordning beskrivelse"
            sisteStillingsendring shouldBe "sisteStillingsprosentendring"
            stillingsprosent shouldBe 100.0
            sisteLoennsendring shouldBe "sisteLoennsendring"
            yrke shouldBe "yrke beskrivelse (Yrkeskode: yrke kode)"
            gyldighetsperiode shouldBe PeriodeDto("2019-11", null)
            fartsomraade shouldBe "fartsområde beskrivelse"
            skipsregister shouldBe "skipsregister beskrivelse"
            skipstype shouldBe "fartøystype beskrivelse"
        }
    }
}