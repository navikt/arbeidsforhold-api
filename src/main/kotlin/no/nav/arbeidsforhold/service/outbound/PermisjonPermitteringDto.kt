package no.nav.arbeidsforhold.service.outbound


data class PermisjonPermitteringDto(

    var periode: PeriodeDto? = null,
    var type: String? = null,
    var prosent: String? = null
)