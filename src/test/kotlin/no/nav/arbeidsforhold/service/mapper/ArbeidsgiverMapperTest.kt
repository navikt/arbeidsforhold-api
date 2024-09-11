package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.mapper.ArbeidsgiverMapper.toOutbound
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.testdata.ArbeidsgiverObjectMother
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArbeidsgiverMapperTest {

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
