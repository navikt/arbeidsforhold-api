package no.nav.arbeidsforhold.util

import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto

object DtoUtils {

    fun hentYrkeForSisteArbeidsavtale(inbound: List<ArbeidsavtaleDto>): String? {
        val sisteArbeidsavtale = inbound.maxByOrNull { it.gyldighetsperiode?.periodeFra.toString() }
        return sisteArbeidsavtale?.yrke
    }
}