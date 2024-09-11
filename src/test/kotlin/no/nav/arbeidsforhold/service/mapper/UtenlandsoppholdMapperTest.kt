package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.service.mapper.UtenlandsoppholdMapper.toOutbound
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.testdata.UtenlandsOppholdObjectMother
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UtenlandsoppholdMapperTest {

    @Test
    fun skalFaaUtenlandsopphold() {
        val inbound = UtenlandsOppholdObjectMother.dummyValues
        val actual: List<UtenlandsoppholdDto> = inbound.map { it.toOutbound() }
        assertNotNull(actual)
        assertEquals(inbound[0].sluttdato, actual[0].periode?.periodeTil)
        assertEquals(inbound[0].startdato, actual[0].periode?.periodeFra)
        assertEquals(inbound[0].land?.beskrivelse, actual[0].land)
        assertEquals(inbound[0].rapporteringsmaaned, actual[0].rapporteringsperiode)
    }
}