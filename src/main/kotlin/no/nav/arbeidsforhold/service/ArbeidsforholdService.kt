package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.mapper.toOutbound
import no.nav.arbeidsforhold.service.mapper.toOutboundDetaljert
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import no.nav.arbeidsforhold.util.isOrganisasjon

class ArbeidsforholdService(
    private val aaregConsumer: AaregConsumer,
    private val eregConsumer: EregConsumer,
) {
    suspend fun hentAlleArbeidsforhold(token: String, fodselsnr: String): List<ArbeidsforholdDto> {
        val arbeidsforhold = aaregConsumer.hentArbeidsforholdmedFnr(token, fodselsnr)
        return arbeidsforhold.map { it.fetchOrgnavnAndMapToOutbound(Arbeidsforhold::toOutbound) }
    }

    suspend fun hentDetaljertArbeidsforhold(token: String, fodselsnr: String, id: Int): ArbeidsforholdDto {
        val arbeidsforhold = aaregConsumer.hentArbeidsforholdmedId(token, fodselsnr, id)
        return arbeidsforhold.fetchOrgnavnAndMapToOutbound(Arbeidsforhold::toOutboundDetaljert)
    }

    private suspend fun Arbeidsforhold.fetchOrgnavnAndMapToOutbound(
        mapper: (Arbeidsforhold, String?, String?) -> ArbeidsforholdDto
    ): ArbeidsforholdDto {
        val arbeidsgiver = arbeidssted?.orgnavnForPeriode(ansettelsesperiode)
        val opplysningspliktig = opplysningspliktig?.orgnavnForPeriode(ansettelsesperiode)
        return mapper(this, arbeidsgiver, opplysningspliktig)
    }

    private suspend fun Identer.orgnavnForPeriode(ansettelsesperiode: Ansettelsesperiode?): String? {
        return identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER).let { orgnr ->
            when {
                orgnr != null && this.isOrganisasjon() -> eregConsumer.hentOrgnavn(orgnr, ansettelsesperiode?.sluttdato)
                else -> orgnr
            }
        }
    }
}
