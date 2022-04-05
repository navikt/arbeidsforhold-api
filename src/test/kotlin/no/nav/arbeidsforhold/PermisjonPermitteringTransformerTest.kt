package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.dto.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.dto.transformer.PermisjonPermitteringTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class PermisjonPermitteringTransformerTest {

    @Test
    fun skalFaaPermisjonPermittering() {
        val inbound = PermisjonPermitteringObjectMother.dummyValues

        val actual: List<PermisjonPermitteringDto> = PermisjonPermitteringTransformer.toOutboundArray(inbound)

        assertNotNull(actual)
        assertEquals(inbound[0].periode?.tom, actual[0].periode?.periodeTil)
        assertEquals(inbound[0].periode?.fom, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].prosent.toString().split(".")[0] + " %", actual[0].prosent)
        assertEquals(inbound[0].type, actual[0].type)
    }

}
