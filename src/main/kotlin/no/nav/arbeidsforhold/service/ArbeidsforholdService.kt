package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.AaregConsumer
import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.dto.Identer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer.toOutboundArray
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.EnkeltArbeidsforholdTransformer.toOutbound
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.firstOfTypeOrNull
import no.nav.arbeidsforhold.util.isOrganisasjon

class ArbeidsforholdService(
    private val aaregConsumer: AaregConsumer,
    private val eregConsumer: EregConsumer,
) {

    suspend fun hentArbeidsforhold(token: String, fodselsnr: String): List<ArbeidsforholdDto> {
        return aaregConsumer.hentArbeidsforholdmedFnr(token, fodselsnr).map {
            val yrke = it.ansettelsesdetaljer
                .toOutboundArray(inkluderYrkeskode = false)
                .hentYrkeForSisteArbeidsavtale()
            val arbgivnavn = it.arbeidssted?.orgnavnForPeriode(it.ansettelsesperiode)
            val opplarbgivnavn = it.opplysningspliktig?.orgnavnForPeriode(it.ansettelsesperiode)

            it.toOutbound(arbgivnavn, opplarbgivnavn, yrke)
        }
    }

    suspend fun hentEttArbeidsforholdmedId(token: String, fodselsnr: String, id: Int): ArbeidsforholdDto {
        val arbeidsforhold = aaregConsumer.hentArbeidsforholdmedId(token, fodselsnr, id)

        val arbgivnavn = arbeidsforhold.arbeidssted?.orgnavnForPeriode(arbeidsforhold.ansettelsesperiode)
        val opplarbgivnavn = arbeidsforhold.opplysningspliktig?.orgnavnForPeriode(arbeidsforhold.ansettelsesperiode)

        return arbeidsforhold.toOutbound(arbgivnavn, opplarbgivnavn)
    }

    private suspend fun Identer.orgnavnForPeriode(ansettelsesperiode: Ansettelsesperiode?): String? {
        val orgnr = identer?.firstOfTypeOrNull(ORGANISASJONSNUMMER)

        return when {
            orgnr != null && this.isOrganisasjon() -> eregConsumer.hentOrgnavn(orgnr, ansettelsesperiode?.sluttdato)
            else -> orgnr
        }
    }

    private fun List<ArbeidsavtaleDto>.hentYrkeForSisteArbeidsavtale(): String? {
        return maxByOrNull { it.gyldighetsperiode?.periodeFra.toString() }?.yrke
    }
}
