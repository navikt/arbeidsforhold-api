package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsgiver
import no.nav.arbeidsforhold.dto.outbound.ArbeidsgiverDto

object ArbeidsgiverTransformer {

    fun toOutbound(inbound: Arbeidsgiver?, arbgivnavn: String?) = ArbeidsgiverDto(

        type = inbound?.type,
        orgnr = inbound?.organisasjonsnummer,
        fnr = inbound?.offentligIdent,
        orgnavn = arbgivnavn

    )
}
