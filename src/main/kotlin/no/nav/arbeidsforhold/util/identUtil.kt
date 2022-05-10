package no.nav.arbeidsforhold.util

import no.nav.arbeidsforhold.consumer.aareg.domain.Ident

const val ORGANISASJONSNUMMER = "ORGANISASJONSNUMMER"
const val AKTORID = "AKTORID"
const val FOLKEREGISTERIDENT = "FOLKEREGISTERIDENT"

fun hentIdent(identer: List<Ident>?, identType: String): String? {
    if (identer == null) {
        return null
    }
    return identer.stream()
        .filter { i -> identType.equals(i.type) }
        .findFirst().orElse(null)?.ident
}