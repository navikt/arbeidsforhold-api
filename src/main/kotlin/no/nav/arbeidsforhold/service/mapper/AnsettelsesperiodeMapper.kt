package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.service.outbound.AnsettelsesperiodeDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object AnsettelsesperiodeMapper {

    fun Ansettelsesperiode?.toOutbound(): AnsettelsesperiodeDto {
        return AnsettelsesperiodeDto(
            periode = PeriodeDto(
                periodeFra = this?.startdato,
                periodeTil = this?.sluttdato
            ),
            varslingskode = this?.varsling?.beskrivelse,
            sluttaarsak = this?.sluttaarsak?.beskrivelse
        )
    }
}
