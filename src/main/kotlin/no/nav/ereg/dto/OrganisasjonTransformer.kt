package no.nav.ereg.dto

import no.nav.ereg.EregOrganisasjon
import no.nav.ereg.dto.outbound.Organisasjon


object OrganisasjonTransformer {

    fun toOutbound(inbound: EregOrganisasjon) = Organisasjon(

            navn = inbound.redigertnavn

    )

}