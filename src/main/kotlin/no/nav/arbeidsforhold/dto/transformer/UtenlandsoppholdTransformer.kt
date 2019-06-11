package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Utenlandsopphold
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutbound(inbound: Utenlandsopphold, landterm: String?) = UtenlandsoppholdDto(
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode),
            land = landterm,
            rapporteringsperiode = inbound.rapporteringsperiode
    )


    fun toOutboundArray(inbound: Array<Utenlandsopphold>?): ArrayList<UtenlandsoppholdDto> {
        val utenlandsoppholdDtoArray = ArrayList<UtenlandsoppholdDto>()

        for (opphold in inbound.orEmpty()) {
            val udto = UtenlandsoppholdDto(
                    land = opphold.landkode,
                    periode = PeriodeTransformer.toOutboundfromPeriode(opphold.periode),
                    rapporteringsperiode = opphold.rapporteringsperiode
            )
            utenlandsoppholdDtoArray.add(udto)
        }

        return utenlandsoppholdDtoArray
    }


}
