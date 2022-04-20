package no.nav.arbeidsforhold.consumer.aareg.domain

data class Gyldighetsperiode(

    /* Fra-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val fom: String? = null,
    /* Til-og-med-dato for periode, format (ISO-8601): yyyy-MM-dd */
    val tom: String? = null
)