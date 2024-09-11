package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.testdata.UtenlandsoppholdFactory.createUtenlandsopphold
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UtenlandsoppholdMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound = createUtenlandsopphold()
        val outbound = inbound.toOutbound()

        assertNotNull(outbound)
        assertEquals(inbound.sluttdato, outbound.periode?.periodeTil)
        assertEquals(inbound.startdato, outbound.periode?.periodeFra)
        assertEquals(inbound.land?.beskrivelse, outbound.land)
        assertEquals(inbound.rapporteringsmaaned, outbound.rapporteringsperiode)
    }
}