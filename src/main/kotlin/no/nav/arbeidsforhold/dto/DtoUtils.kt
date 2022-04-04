package no.nav.arbeidsforhold.dto

import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto

object DtoUtils {

    fun hentYrkeForSisteArbeidsavtale(inbound: List<ArbeidsavtaleDto>): String? {
        val sisteArbeidsavtale = inbound.maxByOrNull { it.gyldighetsperiode?.periodeFra.toString() }
        return sisteArbeidsavtale?.yrke
    }
}