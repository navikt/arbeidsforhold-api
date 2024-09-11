package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.testdata.IdenterFactory.createIdenter
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArbeidsgiverMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound = createIdenter()
        val outbound = inbound.toOutbound("NAV")

        assertNotNull(outbound)
        assertEquals(inbound.type.toString(), outbound.type)
        assertEquals(inbound.identer?.firstOfTypeOrNull(FOLKEREGISTERIDENT), outbound.fnr)
        assertEquals(inbound.identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER), outbound.orgnr)
    }

}
