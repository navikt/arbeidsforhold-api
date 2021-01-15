package no.nav.arbeidsforhold.domain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PermisjonPermittering (

        val periode: Periode? = null,
        /* Id fra opplysningspliktig */
        val permisjonPermitteringId: kotlin.String? = null,
        /* Prosent for permisjon eller permittering */
        val prosent: kotlin.Double? = null,
        val sporingsinformasjon: Sporingsinformasjon? = null,
        /* Permisjon-/permitteringstype (kodeverk: PermisjonsOgPermitteringsBeskrivelse) */
        val type: kotlin.String? = null
)
