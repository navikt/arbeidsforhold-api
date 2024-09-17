package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.service.outbound.AnsettelsesperiodeDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

fun Ansettelsesperiode.toOutbound() = AnsettelsesperiodeDto(
    periode = PeriodeDto(
        periodeFra = startdato,
        periodeTil = sluttdato
    ),
    varslingskode = varsling?.beskrivelse,
    sluttaarsak = sluttaarsak?.beskrivelse
)