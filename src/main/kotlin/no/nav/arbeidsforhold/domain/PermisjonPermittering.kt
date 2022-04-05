package no.nav.arbeidsforhold.domain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PermisjonPermittering (

        val periode: Periode? = null,
        /* Id fra opplysningspliktig */
        val permisjonPermitteringId: String? = null,
        /* Prosent for permisjon eller permittering */
        val prosent: Double? = null,
        val sporingsinformasjon: Sporingsinformasjon? = null,
        /* Permisjon-/permitteringstype (kodeverk: PermisjonsOgPermitteringsBeskrivelse) */
        val type: String? = null
)
