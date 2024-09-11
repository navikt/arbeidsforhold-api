package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Utenlandsopphold
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto

fun Utenlandsopphold.toOutbound() = UtenlandsoppholdDto(
    land = land?.beskrivelse,
    periode = PeriodeDto(startdato, sluttdato),
    rapporteringsperiode = rapporteringsmaaned,
)
