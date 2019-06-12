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
        val inbound = UtenlandsOppholdObjectMother.arrayOfDummyValues
        val actual:  ArrayList<UtenlandsoppholdDto> = UtenlandsoppholdTransformer.toOutboundArray(inbound)
        assertNotNull(actual)
        assertEquals(inbound[0].periode?.tom, actual[0].periode?.periodeTil)
        assertEquals(inbound[0].periode?.fom, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].landkode, actual[0].land)
        assertEquals(inbound[0].rapporteringsperiode, actual[0].rapporteringsperiode)
    }

}