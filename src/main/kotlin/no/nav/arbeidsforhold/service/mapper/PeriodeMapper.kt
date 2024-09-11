package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Rapporteringsmaaneder
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object PeriodeMapper {

    fun Rapporteringsmaaneder?.toOutbound() = PeriodeDto(
        periodeFra = this?.fra,
        periodeTil = this?.til
    )
}
