package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.service.transformer.PermisjonPermitteringTransformer.toOutbound
import no.nav.arbeidsforhold.testdata.PermisjonPermitteringObjectMother
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PermisjonPermitteringTransformerTest {

    @Test
    fun skalFaaPermisjonPermittering() {
        val inbound = PermisjonPermitteringObjectMother.dummyValues

        val actual: List<PermisjonPermitteringDto> = inbound.map { it.toOutbound() }

        assertNotNull(actual)
        assertEquals(inbound[0].sluttdato, actual[0].periode?.periodeTil)
        assertEquals(inbound[0].startdato, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].prosent.toString().split(".")[0] + " %", actual[0].prosent)
        assertEquals(inbound[0].type?.beskrivelse, actual[0].type)
    }

}
