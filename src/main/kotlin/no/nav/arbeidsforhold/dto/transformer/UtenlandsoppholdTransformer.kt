package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Utenlandsopphold
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutbound(inbound: Utenlandsopphold, landterm: String?) = UtenlandsoppholdDto(
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode),
            land = landterm
    )


    fun toOutboundArray(inbound: Array<Utenlandsopphold>?): ArrayList<UtenlandsoppholdDto> {
        var utenlandsoppholdDtoArray = ArrayList<UtenlandsoppholdDto>()

        for (opphold in inbound.orEmpty()) {
            var udto = UtenlandsoppholdDto(
                    land = opphold.landkode,
                    periode = PeriodeTransformer.toOutboundfromPeriode(opphold.periode)
            )
            utenlandsoppholdDtoArray.add(udto)
        }

        return utenlandsoppholdDtoArray
    }


}
