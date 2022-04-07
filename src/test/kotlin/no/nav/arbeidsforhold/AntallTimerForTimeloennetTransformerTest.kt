package no.nav.arbeidsforhold


import no.nav.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.dto.transformer.AntallTimerForTimeloennetTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class AntallTimerForTimeloennetTransformerTest {

    @Test
    fun skalFaaRiktigAntallTimer() {
        val inbound = AntallTimerForTimeloennetObjectMother.dummyValues
        val actual: List<AntallTimerForTimeloennetDto> = AntallTimerForTimeloennetTransformer.toOutboundArray(inbound)
        assertNotNull(actual)
        assertEquals(inbound[0].antallTimer.toString(), actual[0].antallTimer)
        assertEquals(inbound[0].rapporteringsperiode, actual[0].rapporteringsperiode)
        assertEquals(inbound[0].periode?.fom, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].periode?.tom, actual[0].periode?.periodeTil)
    }
}