package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.PermisjonPermittering
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.testdata.createPermisjonPermittering
import org.junit.jupiter.api.Test

class PermisjonPermitteringMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: PermisjonPermittering = createPermisjonPermittering()
        val outbound: PermisjonPermitteringDto = inbound.toOutbound()

        assertSoftly(outbound) {
            periode shouldBe PeriodeDto("01.01.2018", "01.01.2019")
            type shouldBe "type beskrivelse"
            prosent shouldBe "50 %"
        }
    }

    @Test
    fun `should map decimal percentage correctly`() {
        val inbound: PermisjonPermittering = createPermisjonPermittering().copy(prosent = 40.5)
        val outbound: PermisjonPermitteringDto = inbound.toOutbound()

        outbound.prosent shouldBe "40.5 %"
    }
}
