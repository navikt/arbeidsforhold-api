package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.Rapporteringsmaaneder
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

fun Rapporteringsmaaneder?.toOutbound() = PeriodeDto(
    periodeFra = this?.fra,
    periodeTil = this?.til
)