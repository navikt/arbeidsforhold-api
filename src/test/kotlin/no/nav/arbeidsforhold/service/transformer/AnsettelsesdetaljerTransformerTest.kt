package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer.toOutbound
import no.nav.arbeidsforhold.testdata.ArbeidsavtaleObjectMother
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnsettelsesdetaljerTransformerTest {

    @Test
    fun skalFaaArbeidsavtale() {
        val inbound: List<Ansettelsesdetaljer> = ArbeidsavtaleObjectMother.dummyValues
        val actual: List<ArbeidsavtaleDto> = inbound.map { it.toOutbound() }
        assertNotNull(actual)
        assertEquals(inbound[0].skipsregister?.beskrivelse, actual[0].skipsregister)
        assertEquals(inbound[0].fartoeystype?.beskrivelse, actual[0].skipstype)
        assertEquals(inbound[0].fartsomraade?.beskrivelse, actual[0].fartsomraade)
        assertEquals(inbound[0].arbeidstidsordning?.beskrivelse, actual[0].arbeidstidsordning)
        assertEquals(inbound[0].sisteStillingsprosentendring, actual[0].sisteStillingsendring)
    }
}