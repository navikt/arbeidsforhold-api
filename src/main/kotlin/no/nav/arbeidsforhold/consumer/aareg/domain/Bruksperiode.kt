package no.nav.arbeidsforhold.consumer.aareg.domain

data class Bruksperiode(

    /* Fra-tidsstempel for bruksperiode, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val fom: String? = null,
    /* Til-tidsstempel for bruksperiode, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
    val tom: String? = null
)