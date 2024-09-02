package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJON
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.hentIdent
import no.nav.arbeidsforhold.util.isOrganisasjon

object ArbeidsgiverTransformer {

    fun Identer?.toOutbound(arbgivnavn: String?): ArbeidsgiverDto {
        return ArbeidsgiverDto(
            type = if (isOrganisasjon(this)) ORGANISASJON else this?.type,
            orgnr = this?.identer?.let { hentIdent(it, ORGANISASJONSNUMMER) },
            fnr = this?.identer?.let { hentIdent(it, FOLKEREGISTERIDENT) },
            orgnavn = arbgivnavn
        )
    }
}
