package no.nav.arbeidsforhold


import no.nav.arbeidsforhold.domain.Periode
import no.nav.arbeidsforhold.dto.outbound.PeriodeDto
import no.nav.arbeidsforhold.dto.transformer.PeriodeTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class PeriodeTransformerTest {

    @Test
    fun skalTransformerePeriode() {
        val inbound: Periode = PeriodeObjectMother.withDummyValues
        val actual: PeriodeDto = PeriodeTransformer.toOutboundfromPeriode(inbound)

        assertNotNull(actual)
        assertEquals(inbound.tom, actual.periodeTil)
        assertEquals(inbound.fom, actual.periodeFra)
    }
}
