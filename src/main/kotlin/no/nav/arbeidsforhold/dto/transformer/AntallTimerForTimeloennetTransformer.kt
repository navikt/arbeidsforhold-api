package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.AntallTimerForTimeloennet
import no.nav.arbeidsforhold.dto.outbound.AntallTimerForTimeloennetDto

object AntallTimerForTimeloennetTransformer {

    fun toOutbound(inbound: AntallTimerForTimeloennet) = AntallTimerForTimeloennetDto(

            antallTimer = inbound.antallTimer.toString(),
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound.periode)

    )

}
