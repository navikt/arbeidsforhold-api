package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.testdata.createIdenterArbeidssted
import no.nav.arbeidsforhold.testdata.createIdenterArbeidstaker
import org.junit.jupiter.api.Test

class ArbeidsgiverMapperTest {

    @Test
    fun `should map all fields correctly for organisasjon`() {
        val inbound: Identer = createIdenterArbeidssted()
        val outbound: ArbeidsgiverDto = inbound.toOutbound(ARBEIDSGIVER_NAVN)

        assertSoftly(outbound) {
            orgnr shouldBe "123456789"
            fnr.shouldBeNull()
            type shouldBe "Organisasjon"
            orgnavn shouldBe ARBEIDSGIVER_NAVN
        }
    }

    @Test
    fun `should map all fields correctly for ikke-organisasjon`() {
        val inbound: Identer = createIdenterArbeidstaker()
        val outbound: ArbeidsgiverDto = inbound.toOutbound(ARBEIDSGIVER_NAVN)

        assertSoftly(outbound) {
            orgnr.shouldBeNull()
            fnr shouldBe "54321"
            type.shouldBeNull()
            orgnavn shouldBe ARBEIDSGIVER_NAVN
        }
    }

    companion object {
        private const val ARBEIDSGIVER_NAVN = "arbeidsgiver navn"
    }
}
