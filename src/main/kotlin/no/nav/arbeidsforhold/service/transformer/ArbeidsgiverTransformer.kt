package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.hentIdent

object ArbeidsgiverTransformer {

    fun toOutbound(inbound: Identer?, arbgivnavn: String?) = ArbeidsgiverDto(

        type = inbound?.type,
        orgnr = hentIdent(inbound?.identer, ORGANISASJONSNUMMER),
        fnr = hentIdent(inbound?.identer, FOLKEREGISTERIDENT),
        orgnavn = arbgivnavn

    )
}
