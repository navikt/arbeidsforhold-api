package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.PermisjonPermittering
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
        val inbound: PermisjonPermittering = PermisjonPermitteringObjectMother.withDummyValues

        val actual: PermisjonPermitteringDto = PermisjonPermitteringTransformer.toOutbound(inbound)

        assertNotNull(actual)
        assertEquals(inbound.periode?.tom, actual.periode?.periodeTil)
        assertEquals(inbound.periode?.fom, actual.periode?.periodeFra)
        assertEquals(inbound.prosent.toString(), actual.prosent)
        assertEquals(inbound.type, actual.type)
    }

}
