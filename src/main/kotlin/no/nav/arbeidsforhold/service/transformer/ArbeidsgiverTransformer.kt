package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.util.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.util.ORGANISASJON
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import no.nav.arbeidsforhold.util.isOrganisasjon

object ArbeidsgiverTransformer {

    fun Identer?.toOutbound(arbgivnavn: String?): ArbeidsgiverDto {
        return ArbeidsgiverDto(
            type = if (this.isOrganisasjon()) ORGANISASJON else this?.type,
            orgnr = this?.identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER),
            fnr = this?.identer?.firstOfTypeOrNull(FOLKEREGISTERIDENT),
            orgnavn = arbgivnavn
        )
    }
}
