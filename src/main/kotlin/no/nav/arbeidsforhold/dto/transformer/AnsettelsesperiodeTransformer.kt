package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Ansettelsesperiode
import no.nav.arbeidsforhold.domain.Gyldighetsperiode
import no.nav.arbeidsforhold.domain.Periode
import no.nav.arbeidsforhold.dto.outbound.PeriodeDto
import no.nav.arbeidsforhold.dto.outbound.AnsettelsesperiodeDto

object AnsettelsesperiodeTransformer {

    fun toOutbound(inbound: Ansettelsesperiode?) = AnsettelsesperiodeDto (
            periode = PeriodeTransformer.toOutboundfromPeriode(inbound?.periode),
            varslingskode = inbound?.varslingskode,
            sluttaarsak = inbound?.sluttaarsak
    )

    fun toOutboundfromGyldighetsperiode(inbound: Gyldighetsperiode?) = PeriodeDto (
            periodeFra = inbound?.fom,
            periodeTil = inbound?.tom
    )
}
