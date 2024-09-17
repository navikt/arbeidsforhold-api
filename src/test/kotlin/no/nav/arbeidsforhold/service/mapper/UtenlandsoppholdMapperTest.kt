package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.testdata.UtenlandsoppholdFactory.createUtenlandsopphold
import org.junit.jupiter.api.Test

class UtenlandsoppholdMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: Utenlandsopphold = createUtenlandsopphold()
        val outbound: UtenlandsoppholdDto = inbound.toOutbound()

        assertSoftly(outbound) {
            periode shouldBe PeriodeDto("01.01.2018", "01.01.2019")
            rapporteringsperiode shouldBe "rapporteringsmaaned"
            land shouldBe "land beskrivelse"
        }
    }
}