package no.nav.arbeidsforhold.consumer.aareg.dto


data class Utenlandsopphold(

    val startdato: String? = null,
    val sluttdato: String? = null,
    /* Landkode (kodeverk: Landkoder) */
    val land: Kodeverksentitet? = null,
    /* Rapporteringsperiode for utenlandsopphold, format (ISO-8601): yyyy-MM */
    val rapporteringsmaaned: String? = null,
)