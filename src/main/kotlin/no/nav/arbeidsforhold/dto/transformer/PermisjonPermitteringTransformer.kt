package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.PermisjonPermittering
import no.nav.arbeidsforhold.dto.outbound.PermisjonPermitteringDto


object PermisjonPermitteringTransformer {


    fun toOutboundArray(inbound: Array<PermisjonPermittering>?): ArrayList<PermisjonPermitteringDto> {
        var permisjonPermitteringDtoArray = ArrayList<PermisjonPermitteringDto>()

        for (permisjon in inbound.orEmpty()) {
            var pdto = PermisjonPermitteringDto(
                    type = permisjon.type,
                    prosent = permisjon.prosent.toString() + " %",
                    periode = PeriodeTransformer.toOutboundfromPeriode(permisjon.periode)
            )
            permisjonPermitteringDtoArray.add(pdto)
        }

        return permisjonPermitteringDtoArray
    }

}