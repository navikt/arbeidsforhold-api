package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.PermisjonPermittering
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto


object PermisjonPermitteringTransformer {


    fun toOutboundArray(inbound: List<PermisjonPermittering>?): List<PermisjonPermitteringDto> {
        val permisjonPermitteringDtoArray = ArrayList<PermisjonPermitteringDto>()

        for (permisjon in inbound.orEmpty()) {

            val prosentstring = permisjon.prosent?.toString()?.split(".")
            val prosentvist =
                if (prosentstring!![1] != "0") {
                    permisjon.prosent.toString() + " %"
                } else {
                    prosentstring[0] + " %"
                }

            val pdto = PermisjonPermitteringDto(
                type = permisjon.type,
                prosent = prosentvist,
                periode = PeriodeTransformer.toOutboundfromPeriode(permisjon.periode)
            )
            permisjonPermitteringDtoArray.add(pdto)
        }

        return permisjonPermitteringDtoArray
    }

}
