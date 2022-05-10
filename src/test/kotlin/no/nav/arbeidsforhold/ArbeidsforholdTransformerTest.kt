package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.testdata.ArbeidsforholdObjectMother
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
        assertEquals(inbound.id, actual.eksternArbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.startdato, actual.ansettelsesperiode?.periode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.sluttdato, actual.ansettelsesperiode?.periode?.periodeTil)
        assertEquals(inbound.ansettelsesperiode?.varsling, actual.ansettelsesperiode?.varslingskode)
    }
}
