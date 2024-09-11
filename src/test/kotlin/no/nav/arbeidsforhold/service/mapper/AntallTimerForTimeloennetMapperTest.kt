package no.nav.arbeidsforhold.service.mapper


import no.nav.arbeidsforhold.service.mapper.AntallTimerForTimeloennetMapper.toOutbound
import no.nav.arbeidsforhold.testdata.TimerMedTimeloennFactory.createTimerMedTimeloenn
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AntallTimerForTimeloennetMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound = createTimerMedTimeloenn()
        val outbound = inbound.toOutbound()
        
        assertNotNull(outbound)
        assertEquals(inbound.antall.toString(), outbound.antallTimer)
        assertEquals(inbound.rapporteringsmaaned, outbound.rapporteringsperiode)
        assertEquals(inbound.startdato, outbound.periode?.periodeFra)
        assertEquals(inbound.sluttdato, outbound.periode?.periodeTil)
    }
}