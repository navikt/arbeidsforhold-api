package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutboundArray(inbound: List<Utenlandsopphold>?): List<UtenlandsoppholdDto> {
        val utenlandsoppholdDtoArray = ArrayList<UtenlandsoppholdDto>()

        for (opphold in inbound.orEmpty()) {
            val udto = UtenlandsoppholdDto(
                land = opphold.land,
                periode = PeriodeTransformer.toOutboundfromPeriode(opphold.startdato, opphold.sluttdato),
                rapporteringsperiode = opphold.rapporteringsmaaned
            )
            utenlandsoppholdDtoArray.add(udto)
        }

        return utenlandsoppholdDtoArray
    }


}
