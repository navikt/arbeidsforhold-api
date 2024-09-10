package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object AntallTimerForTimeloennetTransformer {

    fun TimerMedTimeloenn.toOutbound(): AntallTimerForTimeloennetDto {
        return AntallTimerForTimeloennetDto(
            antallTimer = antall.toString(),
            periode = PeriodeDto(startdato, sluttdato),
            rapporteringsperiode = rapporteringsmaaned
        )
    }
}
