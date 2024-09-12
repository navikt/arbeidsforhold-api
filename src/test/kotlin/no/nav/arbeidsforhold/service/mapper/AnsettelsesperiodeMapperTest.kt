package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.service.outbound.AnsettelsesperiodeDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.testdata.AnsettelsesperiodeFactory.createAnsettelsesperiode
import org.junit.jupiter.api.Test

class AnsettelsesperiodeMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Ansettelsesperiode = createAnsettelsesperiode()
        val outbound: AnsettelsesperiodeDto = inbound.toOutbound()

        assertSoftly(outbound) {
            periode shouldBe PeriodeDto("01.01.2018", "01.01.2019")
            varslingskode shouldBe "varsling beskrivelse"
            sluttaarsak shouldBe "sluttaarsak beskrivelse"
        }
    }
}