package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.service.outbound.ArbeidsgiverDto
import no.nav.arbeidsforhold.utils.FOLKEREGISTERIDENT
import no.nav.arbeidsforhold.utils.ORGANISASJON
import no.nav.arbeidsforhold.utils.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.utils.firstOfTypeOrNull
import no.nav.arbeidsforhold.utils.isOrganisasjon

fun Identer.toOutbound(arbgivnavn: String?) = ArbeidsgiverDto(
    type = if (isOrganisasjon()) ORGANISASJON else type,
    orgnr = identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER),
    fnr = identer?.firstOfTypeOrNull(FOLKEREGISTERIDENT),
    orgnavn = arbgivnavn
)
