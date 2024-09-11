package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.service.mapper.ArbeidsavtaleMapper.toOutbound
import no.nav.arbeidsforhold.testdata.AnsettelsesdetaljerFactory.createAnsettelsesdetaljer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArbeidsavtaleMapperTest {

    @Test
    fun skalFaaArbeidsavtale() {
        val inbound = createAnsettelsesdetaljer()
        val outbound = inbound.toOutbound()

        assertNotNull(outbound)
        assertEquals(inbound.skipsregister?.beskrivelse, outbound.skipsregister)
        assertEquals(inbound.fartoeystype?.beskrivelse, outbound.skipstype)
        assertEquals(inbound.fartsomraade?.beskrivelse, outbound.fartsomraade)
        assertEquals(inbound.arbeidstidsordning?.beskrivelse, outbound.arbeidstidsordning)
        assertEquals(inbound.sisteStillingsprosentendring, outbound.sisteStillingsendring)
    }
}