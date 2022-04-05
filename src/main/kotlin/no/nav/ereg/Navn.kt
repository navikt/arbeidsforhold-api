package no.nav.ereg

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class Navn(

        val navnelinje1: String? = null,
        val navnelinje2: String? = null,
        val navnelinje3: String? = null,
        val navnelinje4: String? = null,
        val navnelinje5: String? = null

)