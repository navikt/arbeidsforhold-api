package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.OpplysningspliktigArbeidsgiver
import no.nav.arbeidsforhold.dto.outbound.OpplysningspliktigArbeidsgiverDto


object OpplysningspliktigArbeidsgiverTransformer {

    fun toOutbound(inbound: OpplysningspliktigArbeidsgiver?, arbgivnavn: String?) = OpplysningspliktigArbeidsgiverDto(

            type = inbound?.type,
            orgnr = inbound?.organisasjonsnummer,
            fnr = inbound?.offentligIdent,
            orgnavn = arbgivnavn

    )
}
