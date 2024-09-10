package no.nav.arbeidsforhold.service.transformer


import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.service.transformer.AntallTimerForTimeloennetTransformer.toOutbound
import no.nav.arbeidsforhold.testdata.AntallTimerForTimeloennetObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class TimerMedTimeloennTransformerTest {

    @Test
    fun skalFaaRiktigAntallTimer() {
        val inbound = AntallTimerForTimeloennetObjectMother.dummyValues
        val actual: List<AntallTimerForTimeloennetDto> = inbound.map { it.toOutbound() }
        assertNotNull(actual)
        assertEquals(inbound[0].antall.toString(), actual[0].antallTimer)
        assertEquals(inbound[0].rapporteringsmaaned, actual[0].rapporteringsperiode)
        assertEquals(inbound[0].startdato, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].sluttdato, actual[0].periode?.periodeTil)
    }
}