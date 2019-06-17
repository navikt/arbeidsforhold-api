package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.AntallTimerForTimeloennet
import no.nav.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto

object AntallTimerForTimeloennetTransformer {

    fun toOutboundArray(inbound: Array<AntallTimerForTimeloennet>?): ArrayList<AntallTimerForTimeloennetDto>? {
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
