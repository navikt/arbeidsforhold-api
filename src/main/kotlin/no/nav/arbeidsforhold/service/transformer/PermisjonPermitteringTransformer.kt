package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.PermisjonPermittering
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto

object PermisjonPermitteringTransformer {

    fun List<PermisjonPermittering>.toOutboundArray(): List<PermisjonPermitteringDto> {
        return this.sortedBy { it.id }
            .map {
                PermisjonPermitteringDto(
                    type = it.type?.beskrivelse,
                    prosent = requireNotNull(it.prosent).asPercentageString(),
                    periode = PeriodeDto(it.startdato, it.sluttdato)
                )
            }
    }

    private fun Double.asPercentageString(): String {
        val (heltall, desimaler) = toString().split(".")
        return when {
            desimaler == "0" -> "$heltall %"
            else -> "$this %"
        }
    }
}