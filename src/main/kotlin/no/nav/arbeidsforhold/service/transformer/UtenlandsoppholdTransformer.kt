package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun toOutboundArray(inbound: List<Utenlandsopphold>): List<UtenlandsoppholdDto> {
        return inbound.map {
            UtenlandsoppholdDto(
                land = it.land?.beskrivelse,
                periode = PeriodeTransformer.toOutboundfromPeriode(it.startdato, it.sluttdato),
                rapporteringsperiode = it.rapporteringsmaaned
            )
        }
    }
}
