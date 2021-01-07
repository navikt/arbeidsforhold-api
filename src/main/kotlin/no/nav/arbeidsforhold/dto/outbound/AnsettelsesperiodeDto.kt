package no.nav.arbeidsforhold.dto.outbound

data class AnsettelsesperiodeDto (
        val periode : PeriodeDto? = null,
        val varslingskode : String? = null,
        var sluttaarsak : String? = null
)
