package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutboundArray(inbound: List<Utenlandsopphold>?): List<UtenlandsoppholdDto> {
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
