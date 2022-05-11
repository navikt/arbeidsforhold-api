package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.PermisjonPermittering
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto


object PermisjonPermitteringTransformer {

    fun toOutboundArray(
        permitteringer: List<PermisjonPermittering>?,
        permisjoner: List<PermisjonPermittering>?
    ): List<PermisjonPermitteringDto> {
        val permisjonPermitteringDtoArray = ArrayList<PermisjonPermitteringDto>()

        for (permisjonPermittering in (permitteringer.orEmpty() + permisjoner.orEmpty()).sortedBy { it.id }) {

            val prosentstring = permisjonPermittering.prosent?.toString()?.split(".")
            val prosentvist =
                if (prosentstring!![1] != "0") {
                    permisjonPermittering.prosent.toString() + " %"
                } else {
                    prosentstring[0] + " %"
                }

            val pdto = PermisjonPermitteringDto(
                type = permisjonPermittering.type?.beskrivelse,
                prosent = prosentvist,
                periode = PeriodeTransformer.toOutboundfromPeriode(
                    permisjonPermittering.startdato,
                    permisjonPermittering.sluttdato
                )
            )
            permisjonPermitteringDtoArray.add(pdto)
        }

        return permisjonPermitteringDtoArray
    }

}
