package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJON
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.hentIdent
import no.nav.arbeidsforhold.util.isOrganisasjon

object ArbeidsgiverTransformer {

    fun toOutbound(inbound: Identer?, arbgivnavn: String?) = ArbeidsgiverDto(
        type = if (isOrganisasjon(inbound)) ORGANISASJON else inbound?.type,
        orgnr = inbound?.identer?.let { hentIdent(it, ORGANISASJONSNUMMER) },
        fnr = inbound?.identer?.let { hentIdent(it, FOLKEREGISTERIDENT) },
        orgnavn = arbgivnavn
    )
}
