package no.nav.arbeidsforhold.consumer.aareg.domain


data class Utenlandsopphold(

    /* Landkode (kodeverk: Landkoder) */
    val landkode: String? = null,
    val periode: Periode? = null,
    /* Rapporteringsperiode for utenlandsopphold, format (ISO-8601): yyyy-MM */
    val rapporteringsperiode: String? = null,
    val sporingsinformasjon: Sporingsinformasjon? = null
)