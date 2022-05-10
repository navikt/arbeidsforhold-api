package no.nav.arbeidsforhold.consumer.aareg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class PermisjonPermittering(

    val startdato: String? = null,
    val sluttdato: String? = null,
    /* Id fra opplysningspliktig */
    val id: String? = null,
    /* Prosent for permisjon eller permittering */
    val prosent: Double? = null,
    /* Permisjon-/permitteringstype (kodeverk: PermisjonsOgPermitteringsBeskrivelse) */
    val type: String? = null
)
