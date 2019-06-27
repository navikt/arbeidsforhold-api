package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.PermisjonPermittering
import no.nav.arbeidsforhold.dto.outbound.PermisjonPermitteringDto
import java.math.RoundingMode
import kotlin.math.ulp


object PermisjonPermitteringTransformer {


    fun toOutboundArray(inbound: Array<PermisjonPermittering>?): ArrayList<PermisjonPermitteringDto> {
        val permisjonPermitteringDtoArray = ArrayList<PermisjonPermitteringDto>()

        for (permisjon in inbound.orEmpty()) {

            val prosentstring = permisjon.prosent?.toString()?.split(".")
            var prosentvist =
                    if (!prosentstring!![1].equals("0")) {
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
