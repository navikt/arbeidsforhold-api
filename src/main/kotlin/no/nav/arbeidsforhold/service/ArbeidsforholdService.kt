package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer.toOutboundDetaljert
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import no.nav.arbeidsforhold.util.isOrganisasjon

class ArbeidsforholdService(
    private val aaregConsumer: AaregConsumer,
    private val eregConsumer: EregConsumer,
) {

    suspend fun hentArbeidsforhold(token: String, fodselsnr: String): List<ArbeidsforholdDto> {
        return aaregConsumer.hentArbeidsforholdmedFnr(token, fodselsnr).map { arbeidsforhold ->
            val (arbeidsgiver, opplysningspliktig) = arbeidsforhold.orgnavn()
            arbeidsforhold.toOutbound(arbeidsgiver, opplysningspliktig)
        }
    }

    suspend fun hentEttArbeidsforholdmedId(token: String, fodselsnr: String, id: Int): ArbeidsforholdDto {
        aaregConsumer.hentArbeidsforholdmedId(token, fodselsnr, id).let { arbeidsforhold ->
            val (arbeidsgiver, opplysningspliktig) = arbeidsforhold.orgnavn()
            return arbeidsforhold.toOutboundDetaljert(arbeidsgiver, opplysningspliktig)
        }
    }

    private suspend fun Arbeidsforhold.orgnavn(): Pair<String?, String?> {
        val arbeidsgiver = arbeidssted?.orgnavnForPeriode(ansettelsesperiode)
        val opplysningspliktig = opplysningspliktig?.orgnavnForPeriode(ansettelsesperiode)

        return arbeidsgiver to opplysningspliktig
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
