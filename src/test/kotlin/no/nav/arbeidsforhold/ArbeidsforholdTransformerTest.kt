package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArbeidsforholdTransformerTest {

    @Test
    fun skalFaaArbeidsforhold() {
        val inbound: Arbeidsforhold = ArbeidsforholdObjectMother.withDummyValues
        val actual: ArbeidsforholdDto = ArbeidsforholdTransformer.toOutbound(inbound, "NAV IKT", "NAV", null)
        assertNotNull(actual)
        assertEquals(inbound.navArbeidsforholdId, actual.navArbeidsforholdId)
        assertEquals(inbound.arbeidsforholdId, actual.eksternArbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.periode?.fom, actual.ansettelsesperiode?.periode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.periode?.tom, actual.ansettelsesperiode?.periode?.periodeTil)
        assertEquals(inbound.ansettelsesperiode?.varslingskode, actual.ansettelsesperiode?.varslingskode)
    }
}
