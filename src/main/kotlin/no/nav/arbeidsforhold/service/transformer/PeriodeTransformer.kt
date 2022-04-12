package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Gyldighetsperiode
import no.nav.arbeidsforhold.consumer.aareg.domain.Periode
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object PeriodeTransformer {

    fun toOutboundfromPeriode(inbound: Periode?) = PeriodeDto(
        periodeFra = inbound?.fom,
        periodeTil = inbound?.tom
    )

    fun toOutboundfromGyldighetsperiode(inbound: Gyldighetsperiode?) = PeriodeDto(
        periodeFra = inbound?.fom,
        periodeTil = inbound?.tom
    )
}
