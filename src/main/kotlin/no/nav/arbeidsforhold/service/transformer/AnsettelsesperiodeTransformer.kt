package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.service.outbound.AnsettelsesperiodeDto

object AnsettelsesperiodeTransformer {

    fun toOutbound(inbound: Ansettelsesperiode?) = AnsettelsesperiodeDto(
        periode = PeriodeTransformer.toOutboundfromPeriode(inbound?.startdato, inbound?.sluttdato),
        varslingskode = inbound?.varsling?.beskrivelse,
        sluttaarsak = inbound?.sluttaarsak?.beskrivelse
    )
}
