package no.nav.arbeidsforhold.dto.outbound

data class UtenlandsoppholdDto (

        var periode: PeriodeDto? = null,
        var rapporteringsperiode: String? = null,
        var land: String? = null
)