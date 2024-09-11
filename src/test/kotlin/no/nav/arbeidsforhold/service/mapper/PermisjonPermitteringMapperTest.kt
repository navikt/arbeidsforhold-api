package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.testdata.PermisjonPermitteringFactory.createPermisjonPermittering
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PermisjonPermitteringMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound = createPermisjonPermittering()
        val outbound = inbound.toOutbound()

        assertNotNull(outbound)
        assertEquals(inbound.sluttdato, outbound.periode?.periodeTil)
        assertEquals(inbound.startdato, outbound.periode?.periodeFra)
        assertEquals("${inbound.prosent.toString().split(".")[0]} %", outbound.prosent)
        assertEquals(inbound.type?.beskrivelse, outbound.type)
    }
}
