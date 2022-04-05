package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.AntallTimerForTimeloennet
import no.nav.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto

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
