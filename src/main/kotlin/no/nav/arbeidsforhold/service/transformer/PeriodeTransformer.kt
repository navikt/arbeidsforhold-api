package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Rapporteringsmaaneder
import no.nav.arbeidsforhold.service.outbound.PeriodeDto

object PeriodeTransformer {

    fun toOutboundfromPeriode(startdato: String?, sluttdato: String?) = PeriodeDto(
        periodeFra = startdato,
        periodeTil = sluttdato
    )

    fun toOutboundfromGyldighetsperiode(inbound: Rapporteringsmaaneder?) = PeriodeDto(
        periodeFra = inbound?.fra,
        periodeTil = inbound?.til
    )
}
