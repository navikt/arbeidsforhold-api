package no.nav.arbeidsforhold.domain

data class Gyldighetsperiode (

        /* Fra-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
        val fom: kotlin.String? = null,
        /* Til-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
        val tom: kotlin.String? = null
)