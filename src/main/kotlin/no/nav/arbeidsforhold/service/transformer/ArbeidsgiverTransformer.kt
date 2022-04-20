package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsgiver
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto

object ArbeidsgiverTransformer {

    fun toOutbound(inbound: Arbeidsgiver?, arbgivnavn: String?) = ArbeidsgiverDto(

        type = inbound?.type,
        orgnr = inbound?.organisasjonsnummer,
        fnr = inbound?.offentligIdent,
        orgnavn = arbgivnavn

    )
}
