package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.domain.Identer
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.consumer.ereg.domain.EregOrganisasjon
import no.nav.arbeidsforhold.consumer.ereg.domain.Navn
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.service.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.util.DtoUtils
import no.nav.arbeidsforhold.util.ORGANISASJONSNUMMER
import no.nav.arbeidsforhold.util.hentIdent
import no.nav.arbeidsforhold.util.isOrganisasjon
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArbeidsforholdService @Autowired constructor(
    private var arbeidsforholdConsumer: ArbeidsforholdConsumer,
    private var eregConsumer: EregConsumer,
) {

    fun hentArbeidsforhold(fodselsnr: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr)
        val arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {
            val yrke = DtoUtils.hentYrkeForSisteArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(arbeidsforhold.ansettelsesdetaljer))
            val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.arbeidssted, arbeidsforhold.ansettelsesperiode)
            val opplarbgivnavn =
                hentArbGiverOrgNavn(arbeidsforhold.opplysningspliktig, arbeidsforhold.ansettelsesperiode)
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

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int): ArbeidsforholdDto {
        val arbeidsforhold = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id)

        val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.arbeidssted, arbeidsforhold.ansettelsesperiode)
        val opplarbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.opplysningspliktig, arbeidsforhold.ansettelsesperiode)

        return EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn)
    }


    private fun hentArbGiverOrgNavn(identer: Identer?, ansettelsesperiode: Ansettelsesperiode?): String? {
        val orgnr = hentIdent(identer?.identer, ORGANISASJONSNUMMER)
        if (isOrganisasjon(identer)) {
            val organisasjon: EregOrganisasjon? = eregConsumer.hentOrgnavn(orgnr, ansettelsesperiode?.sluttdato)
            if (organisasjon != null) {
                return concatenateNavn(organisasjon.navn)
            }
        }
        return orgnr
    }

    private fun concatenateNavn(navn: Navn?): String {
        var orgnavn = ""
        if (!navn?.navnelinje1.isNullOrEmpty()) orgnavn += navn?.navnelinje1.orEmpty()
        if (!navn?.navnelinje2.isNullOrEmpty()) orgnavn += " " + navn?.navnelinje2.orEmpty()
        if (!navn?.navnelinje3.isNullOrEmpty()) orgnavn += " " + navn?.navnelinje3.orEmpty()
        if (!navn?.navnelinje4.isNullOrEmpty()) orgnavn += " " + navn?.navnelinje4.orEmpty()
        if (!navn?.navnelinje5.isNullOrEmpty()) orgnavn += " " + navn?.navnelinje5.orEmpty()

        return orgnavn
    }
}
