package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto

object AntallTimerForTimeloennetTransformer {

    fun toOutboundArray(inbound: List<TimerMedTimeloenn>): List<AntallTimerForTimeloennetDto> {
        return inbound.map {
            AntallTimerForTimeloennetDto(
                antallTimer = it.antall.toString(),
                periode = PeriodeTransformer.toOutboundfromPeriode(it.startdato, it.sluttdato),
                rapporteringsperiode = it.rapporteringsmaaned
            )
        }
    }
}
