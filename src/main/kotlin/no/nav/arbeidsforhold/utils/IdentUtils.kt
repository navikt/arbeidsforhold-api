package no.nav.arbeidsforhold.utils

import no.nav.arbeidsforhold.consumer.aareg.dto.Ident
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer

const val ORGANISASJONSNUMMER = "ORGANISASJONSNUMMER"
const val FOLKEREGISTERIDENT = "FOLKEREGISTERIDENT"

private const val HOVEDENHET = "Hovedenhet"
private const val UNDERENHET = "Underenhet"

const val ORGANISASJON = "Organisasjon"

fun List<Ident>.firstOfTypeOrNull(identType: String) = firstOrNull { identType == it.type }?.ident

fun Identer?.isOrganisasjon() = this?.type.equals(HOVEDENHET) || this?.type.equals(UNDERENHET)