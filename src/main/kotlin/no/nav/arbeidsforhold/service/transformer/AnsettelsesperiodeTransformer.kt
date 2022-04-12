package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesperiode
import no.nav.arbeidsforhold.service.outbound.AnsettelsesperiodeDto

object AnsettelsesperiodeTransformer {

    fun toOutbound(inbound: Ansettelsesperiode?) = AnsettelsesperiodeDto(
        periode = PeriodeTransformer.toOutboundfromPeriode(inbound?.periode),
        varslingskode = inbound?.varslingskode,
        sluttaarsak = inbound?.sluttaarsak
    )
}
