package no.nav.arbeidsforhold.service.mapper

import no.nav.arbeidsforhold.consumer.aareg.dto.PermisjonPermittering
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto

fun PermisjonPermittering.toOutbound() = PermisjonPermitteringDto(
    type = type?.beskrivelse,
    prosent = requireNotNull(prosent).asPercentageString(),
    periode = PeriodeDto(startdato, sluttdato)
)


fun joinToSortedList(
    first: List<PermisjonPermittering>,
    second: List<PermisjonPermittering>
) = first.plus(second).sortedBy { it.id }


private fun Double.asPercentageString(): String {
    val (heltall, desimaler) = toString().split(".")
    return when {
        desimaler == "0" -> "$heltall %"
        else -> "$this %"
    }
}