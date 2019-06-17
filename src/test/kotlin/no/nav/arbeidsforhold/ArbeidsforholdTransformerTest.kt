package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArbeidsforholdTransformerTest {

    @Test
    fun skalFaaArbeidsforhold() {
        val inbound: Arbeidsforhold = ArbeidsforholdObjectMother.withDummyValues
        val actual: ArbeidsforholdDto = ArbeidsforholdTransformer.toOutbound(inbound, "NAV IKT", "NAV")
        assertNotNull(actual)
        assertEquals(inbound.navArbeidsforholdId, actual.navArbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.periode?.fom, actual.ansettelsesperiode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.periode?.tom, actual.ansettelsesperiode?.periodeTil)

    }
}