package no.nav.arbeidsforhold.service.outbound

data class AnsettelsesperiodeDto(
    val periode: PeriodeDto? = null,
    val varslingskode: String? = null,
    var sluttaarsak: String? = null
)
