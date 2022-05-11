package no.nav.arbeidsforhold.util

import no.nav.arbeidsforhold.consumer.aareg.domain.Ident
import no.nav.arbeidsforhold.consumer.aareg.domain.Identer

const val ORGANISASJONSNUMMER = "ORGANISASJONSNUMMER"
const val AKTORID = "AKTORID"
const val FOLKEREGISTERIDENT = "FOLKEREGISTERIDENT"

private const val HOVEDENHET = "Hovedenhet"
private const val UNDERENHET = "Underenhet"

const val ORGANISASJON = "Organisasjon"

fun hentIdent(identer: List<Ident>?, identType: String): String? {
    if (identer == null) {
        return null
    }
    return identer.stream()
        .filter { i -> identType == i.type }
        .findFirst().orElse(null)?.ident
}

fun isOrganisasjon(identer: Identer?): Boolean {
    return identer?.type.equals(HOVEDENHET) || identer?.type.equals(UNDERENHET)
}