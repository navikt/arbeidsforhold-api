package no.nav.arbeidsforhold.consumer.aareg.domain


data class Utenlandsopphold(

    val startdato: String? = null,
    val sluttdato: String? = null,
    /* Landkode (kodeverk: Landkoder) */
    val land: String? = null,
    /* Rapporteringsperiode for utenlandsopphold, format (ISO-8601): yyyy-MM */
    val rapporteringsmaaned: String? = null,
)