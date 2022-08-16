package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.service.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.util.DtoUtils
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.hentIdent
import no.nav.arbeidsforhold.util.isOrganisasjon

class ArbeidsforholdService(
    private var aaregConsumer: AaregConsumer,
    private var eregConsumer: EregConsumer,
) {

    suspend fun hentArbeidsforhold(token: String, fodselsnr: String): List<ArbeidsforholdDto> {
        val inbound = aaregConsumer.hentArbeidsforholdmedFnr(token, fodselsnr)
        val arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {
            val yrke =
                DtoUtils.hentYrkeForSisteArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(arbeidsforhold.ansettelsesdetaljer))
            val arbgivnavn = arbeidsforhold.arbeidssted?.let {
                hentArbGiverOrgNavn(it, arbeidsforhold.ansettelsesperiode)
            }
            val opplarbgivnavn = arbeidsforhold.opplysningspliktig?.let {
                hentArbGiverOrgNavn(it, arbeidsforhold.ansettelsesperiode)
            }
            arbeidsforholdDtos.add(
                ArbeidsforholdTransformer.toOutbound(
                    arbeidsforhold,
                    arbgivnavn,
                    opplarbgivnavn,
                    yrke
                )
            )
        }
        return arbeidsforholdDtos
    }

    suspend fun hentEttArbeidsforholdmedId(token: String, fodselsnr: String, id: Int): ArbeidsforholdDto {
        val arbeidsforhold = aaregConsumer.hentArbeidsforholdmedId(token, fodselsnr, id)

        val arbgivnavn = arbeidsforhold.arbeidssted?.let { hentArbGiverOrgNavn(it, arbeidsforhold.ansettelsesperiode) }
        val opplarbgivnavn = arbeidsforhold.opplysningspliktig?.let {
            hentArbGiverOrgNavn(it, arbeidsforhold.ansettelsesperiode)
        }

        return EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn)
    }


    private suspend fun hentArbGiverOrgNavn(identer: Identer, ansettelsesperiode: Ansettelsesperiode?): String? {
        val orgnr = identer.identer?.let { hentIdent(it, ORGANISASJONSNUMMER) } ?: return null

        if (isOrganisasjon(identer)) {
            return eregConsumer.hentOrgnavn(orgnr, ansettelsesperiode?.sluttdato)
        }
        return orgnr
    }
}
