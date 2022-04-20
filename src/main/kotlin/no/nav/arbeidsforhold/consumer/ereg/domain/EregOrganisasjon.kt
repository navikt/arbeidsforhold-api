package no.nav.arbeidsforhold.consumer.ereg.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class EregOrganisasjon(

    val navn: Navn? = null

)