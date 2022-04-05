package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.Arbeidsgiver
import no.nav.arbeidsforhold.dto.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsgiverTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class ArbeidsgiverTransformerTest {

    @Test
    fun skalFaArbeidsgiver() {
        val inbound: Arbeidsgiver = ArbeidsgiverObjectMother.withDummyValues

        val actual: ArbeidsgiverDto = ArbeidsgiverTransformer.toOutbound(inbound, "NAV")
        assertNotNull(actual)
        assertEquals(inbound.type.toString(), actual.type)
        assertEquals(inbound.offentligIdent, actual.fnr)
        assertEquals(inbound.organisasjonsnummer, actual.orgnr)
    }

}
