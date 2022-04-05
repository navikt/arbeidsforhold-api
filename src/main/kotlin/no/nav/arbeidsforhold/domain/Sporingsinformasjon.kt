package no.nav.arbeidsforhold.domain

data class Sporingsinformasjon (

        /* Brukernavn for endring */
        val endretAv: String? = null,
        /* Kilde for endring */
        val endretKilde: String? = null,
        /* Kildereferanse for endring */
        val endretKildereferanse: String? = null,
        /* Tidspunkt for endring, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
        val endretTidspunkt: String? = null,
        /* Brukernavn for opprettelse */
        val opprettetAv: String? = null,
        /* Kilde for opprettelse */
        val opprettetKilde: String? = null,
        /* Kildereferanse for opprettelse */
        val opprettetKildereferanse: String? = null,
        /* Tidspunkt for opprettelse, format (ISO-8601): yyyy-MM-dd'T'HH:mm[:ss[.SSSSSSSSS]] */
        val opprettetTidspunkt: String? = null
)