package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.AntallTimerForTimeloennet
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto

object AntallTimerForTimeloennetTransformer {

    fun toOutboundArray(inbound: List<AntallTimerForTimeloennet>?): List<AntallTimerForTimeloennetDto> {
        val antallDtoArray = ArrayList<AntallTimerForTimeloennetDto>()

        for (antall in inbound.orEmpty()) {
            val antallDto = AntallTimerForTimeloennetDto(
                antallTimer = antall.antallTimer.toString(),
                periode = PeriodeTransformer.toOutboundfromPeriode(antall.periode),
                rapporteringsperiode = antall.rapporteringsperiode
            )
            antallDtoArray.add(antallDto)
        }

        return antallDtoArray
    }
}
