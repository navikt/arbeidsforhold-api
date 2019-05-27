package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.domain.OpplysningspliktigArbeidsgiver
import no.nav.arbeidsforhold.dto.outbound.OpplysningspliktigArbeidsgiverDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.OpplysningspliktigArbeidsgiverTransformer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(PER_CLASS)
class OpplysningspliktigArbeidsgiverTransformerTest

@Test
fun skalFaaOpplysningspliktigArbeidsgiver() {
    val inbound: OpplysningspliktigArbeidsgiver = OpplysningspliktigArbeidsgiverObjectMother.withDummyValues
    val actual: OpplysningspliktigArbeidsgiverDto = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound, "NAV")
    assertNotNull(actual)
    assertEquals(inbound.type.toString(), actual.type)

}


