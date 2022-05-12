package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto

object AntallTimerForTimeloennetTransformer {

    fun toOutboundArray(inbound: List<TimerMedTimeloenn>?): List<AntallTimerForTimeloennetDto> {
        val antallDtoArray = ArrayList<AntallTimerForTimeloennetDto>()

        for (antall in inbound.orEmpty()) {
            val antallDto = AntallTimerForTimeloennetDto(
                antallTimer = antall.antall.toString(),
                periode = PeriodeTransformer.toOutboundfromPeriode(antall.startdato, antall.sluttdato),
                rapporteringsperiode = antall.rapporteringsmaaned
            )
            antallDtoArray.add(antallDto)
        }

        return antallDtoArray
    }
}
