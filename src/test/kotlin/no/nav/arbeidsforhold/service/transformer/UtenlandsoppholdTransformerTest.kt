package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.service.transformer.UtenlandsoppholdTransformer.toOutboundArray
import no.nav.arbeidsforhold.testdata.UtenlandsOppholdObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class UtenlandsoppholdTransformerTest {

    @Test
    fun skalFaaUtenlandsopphold() {
        val inbound = UtenlandsOppholdObjectMother.dummyValues
        val actual: List<UtenlandsoppholdDto> = inbound.toOutboundArray()
        assertNotNull(actual)
        assertEquals(inbound[0].sluttdato, actual[0].periode?.periodeTil)
        assertEquals(inbound[0].startdato, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].land?.beskrivelse, actual[0].land)
        assertEquals(inbound[0].rapporteringsmaaned, actual[0].rapporteringsperiode)
    }

}