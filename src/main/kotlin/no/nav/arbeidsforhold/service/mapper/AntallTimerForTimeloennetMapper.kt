package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

fun TimerMedTimeloenn.toOutbound() = AntallTimerForTimeloennetDto(
    antallTimer = antall.toString(),
    periode = PeriodeDto(startdato, sluttdato),
    rapporteringsperiode = rapporteringsmaaned
)