package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object AntallTimerForTimeloennetTransformer {

    fun List<TimerMedTimeloenn>.toOutboundArray(): List<AntallTimerForTimeloennetDto> {
        return map {
            AntallTimerForTimeloennetDto(
                antallTimer = it.antall.toString(),
                periode = PeriodeDto(it.startdato, it.sluttdato),
                rapporteringsperiode = it.rapporteringsmaaned
            )
        }
    }
}
