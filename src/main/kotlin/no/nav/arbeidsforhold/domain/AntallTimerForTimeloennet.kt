package no.nav.arbeidsforhold.domain

data class AntallTimerForTimeloennet (

        /* Antall timer */
        val antallTimer: Double? = null,
        val periode: Periode? = null,
        /* Rapporteringsperiode for antall timer med timel&oslash;nn, format (ISO-8601): yyyy-MM */
        val rapporteringsperiode: String? = null,
        val sporingsinformasjon: Sporingsinformasjon? = null
)