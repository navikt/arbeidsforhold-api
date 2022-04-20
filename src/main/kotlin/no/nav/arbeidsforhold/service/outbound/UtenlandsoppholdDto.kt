package no.nav.arbeidsforhold.service.outbound

data class UtenlandsoppholdDto(

    var periode: PeriodeDto? = null,
    var rapporteringsperiode: String? = null,
    var land: String? = null
)