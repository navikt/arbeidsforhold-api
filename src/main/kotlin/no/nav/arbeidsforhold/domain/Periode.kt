package no.nav.arbeidsforhold.domain

data class Periode(

    /* Fra-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val fom: String? = null,
    /* Til-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val tom: String? = null
)