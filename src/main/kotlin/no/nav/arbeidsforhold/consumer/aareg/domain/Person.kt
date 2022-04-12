package no.nav.arbeidsforhold.consumer.aareg.domain

data class Person(

    /* Akt&oslash;r-id */
    val aktoerId: String? = null,
    /* Gjeldende offentlig ident */
    val offentligIdent: String? = null,
    /* Type: Organisasjon eller Person */
    val type: String? = null
)