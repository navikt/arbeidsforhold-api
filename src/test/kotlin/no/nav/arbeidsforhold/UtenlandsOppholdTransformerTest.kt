package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Utenlandsopphold
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.dto.transformer.UtenlandsoppholdTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class UtenlandsoppholdTransformerTest {

    @Test
    fun skalFaaUtenlandsopphold() {
        val inbound: Utenlandsopphold = UtenlandsOppholdObjectMother.withDummyValues
        val actual: UtenlandsoppholdDto = UtenlandsoppholdTransformer.toOutbound(inbound, "NOR")
        assertNotNull(actual)
        assertEquals(inbound.periode?.tom, actual.periode?.periodeTil)
        assertEquals(inbound.periode?.fom, actual.periode?.periodeFra)
        assertEquals(inbound.landkode, actual.land)
    }

}