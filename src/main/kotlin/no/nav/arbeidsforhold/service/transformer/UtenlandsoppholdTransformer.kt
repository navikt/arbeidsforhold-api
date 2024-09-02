package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto

object UtenlandsoppholdTransformer {

    fun List<Utenlandsopphold>.toOutboundArray(): List<UtenlandsoppholdDto> {
        return map {
            UtenlandsoppholdDto(
                land = it.land?.beskrivelse,
                periode = PeriodeDto(it.startdato, it.sluttdato),
                rapporteringsperiode = it.rapporteringsmaaned
            )
        }
    }
}
