package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.service.mapper.ArbeidsforholdMapper.toOutbound
import no.nav.arbeidsforhold.testdata.ArbeidsforholdFactory.createArbeidsforhold
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArbeidsforholdMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound = createArbeidsforhold()
        val outbound = inbound.toOutbound("NAV IKT", "NAV")

        assertNotNull(outbound)
        assertEquals(inbound.navArbeidsforholdId, outbound.navArbeidsforholdId)
        assertEquals(inbound.id, outbound.eksternArbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.startdato, outbound.ansettelsesperiode?.periode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.sluttdato, outbound.ansettelsesperiode?.periode?.periodeTil)
        assertEquals(inbound.ansettelsesperiode?.varsling?.beskrivelse, outbound.ansettelsesperiode?.varslingskode)
    }
}
