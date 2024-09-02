package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsgiverTransformer.toOutbound
import no.nav.arbeidsforhold.testdata.ArbeidsgiverObjectMother
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class IdenterTransformerTest {

    @Test
    fun skalFaArbeidsgiver() {
        val inbound: Identer = ArbeidsgiverObjectMother.withDummyValues

        val actual: ArbeidsgiverDto = inbound.toOutbound("NAV")
        assertNotNull(actual)
        assertEquals(inbound.type.toString(), actual.type)
        assertEquals(inbound.identer?.firstOfTypeOrNull(FOLKEREGISTERIDENT), actual.fnr)
        assertEquals(inbound.identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER), actual.orgnr)
    }

}
