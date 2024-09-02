package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Rapporteringsmaaneder
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object PeriodeTransformer {

    fun Rapporteringsmaaneder?.toOutbound() = PeriodeDto(
        periodeFra = this?.fra,
        periodeTil = this?.til
    )
}
