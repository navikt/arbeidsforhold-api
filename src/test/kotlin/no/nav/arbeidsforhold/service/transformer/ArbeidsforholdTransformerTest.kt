package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer.toOutbound
import no.nav.arbeidsforhold.testdata.ArbeidsforholdObjectMother
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArbeidsforholdTransformerTest {

    @Test
    fun skalFaaArbeidsforhold() {
        val inbound: Arbeidsforhold = ArbeidsforholdObjectMother.withDummyValues
        val actual: ArbeidsforholdDto = inbound.toOutbound("NAV IKT", "NAV")
        assertNotNull(actual)
        assertEquals(inbound.navArbeidsforholdId, actual.navArbeidsforholdId)
        assertEquals(inbound.id, actual.eksternArbeidsforholdId)
        assertEquals(inbound.ansettelsesperiode?.startdato, actual.ansettelsesperiode?.periode?.periodeFra)
        assertEquals(inbound.ansettelsesperiode?.sluttdato, actual.ansettelsesperiode?.periode?.periodeTil)
        assertEquals(inbound.ansettelsesperiode?.varsling?.beskrivelse, actual.ansettelsesperiode?.varslingskode)
    }
}
