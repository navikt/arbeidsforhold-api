package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Arbeidsavtale
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsavtaleTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArbeidsavtaleTransformerTest {

    @Test
    fun skalFaaArbeidsavtale() {
        val inbound: Array<Arbeidsavtale> = ArbeidsavtaleObjectMother.arrayOfDummyValues
        val actual: List<ArbeidsavtaleDto> = ArbeidsavtaleTransformer.toOutboundArray(inbound)
        assertNotNull(actual)
        assertEquals(inbound[0].skipsregister, actual[0].skipsregister)
        assertEquals(inbound[0].skipstype, actual[0].skipstype)
        assertEquals(inbound[0].fartsomraade, actual[0].fartsomraade)
        assertEquals(inbound[0].arbeidstidsordning, actual[0].arbeidstidsordning)
        assertEquals(inbound[0].sistStillingsendring, actual[0].sisteStillingsendring)

    }
}