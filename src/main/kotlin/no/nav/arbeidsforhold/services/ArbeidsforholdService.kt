package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.dto.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsavtaleTransformer
import no.nav.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.dto.DtoUtils
import no.nav.arbeidsforhold.dto.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.services.sts.STSConsumer
import no.nav.ereg.Navn
import no.nav.arbeidsforhold.services.kodeverk.KodeverkConsumer
import no.nav.arbeidsforhold.services.kodeverk.api.GetKodeverkKoderBetydningerResponse
import no.nav.ereg.EregOrganisasjon
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArbeidsforholdService @Autowired constructor(
        private var arbeidsforholdConsumer: ArbeidsforholdConsumer,
        private var stsConsumer: STSConsumer,
        private var eregConsumer: EregConsumer,
        private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(ArbeidsforholdService::class.java)
    private val organisasjon = "Organisasjon"
    private val kodeverkspraak = "nb"

    fun hentFSSToken(): String? {
        val fssToken = stsConsumer.fssToken
        val strippedToken = fssToken.access_token
        return strippedToken
    }

    fun hentArbeidsforhold(fodselsnr: String, fssToken: String?): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr, fssToken)
        val arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {
            var yrke = DtoUtils.hentYrkeForSisteArbeidsavtale(
                    ArbeidsavtaleTransformer.toOutboundArray(arbeidsforhold.arbeidsavtaler))?.run { getYrkeTerm(kodeverkConsumer.hentYrke(), this, false) }
            val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold)
            val opplarbgivnavn = hentOpplysningspliktigOrgNavn(arbeidsforhold)
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn, yrke))
        }
        return arbeidsforholdDtos
    }

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String?): ArbeidsforholdDto {
        val arbeidsforhold = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)

        val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold)
        val opplarbgivnavn = hentOpplysningspliktigOrgNavn(arbeidsforhold)
        val arbeidsforholdDto = EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn)

        val yrkeskode = arbeidsforholdDto.yrke
        val typekode = arbeidsforholdDto.type
        val arbeidstidsordningkode = arbeidsforholdDto.arbeidstidsordning
        val skipstypekode = arbeidsforholdDto.skipstype
        val skipsregisterkode = arbeidsforholdDto.skipsregister
        val fartsomraadekode = arbeidsforholdDto.fartsomraade

        val yrke = kodeverkConsumer.hentYrke()
        val type = kodeverkConsumer.hentArbeidsforholdstyper()
        val arbeidstidsordning = kodeverkConsumer.hentArbeidstidsordningstyper()
        val skipstype = kodeverkConsumer.hentSkipstyper()
        val skipsregister = kodeverkConsumer.hentSkipsregister()
        val fartsomraade = kodeverkConsumer.hentFartsomraade()

        settKodeverkVerdier(arbeidsforholdDto, yrke, type, arbeidstidsordning, skipsregister, skipstype, fartsomraade)
        settInnKodeverksverdierIUtenlandsopphold(arbeidsforholdDto.utenlandsopphold)
        settInnKodeverksverdierIArbeidsavtale(arbeidsforholdDto, yrkeskode, typekode, arbeidstidsordningkode, skipstypekode, skipsregisterkode, fartsomraadekode)
        settInnKodeverksverdierIPermitteringer(arbeidsforholdDto.permisjonPermittering)

        return arbeidsforholdDto
    }

    private fun settInnKodeverksverdierIArbeidsavtale(arbeidsforhold: ArbeidsforholdDto?, yrke: String?, type: String?, arbeidstidsordning: String?, skipsregister: String?, skipstype: String?, fartsomraade: String?) {
        //Setter inn kodeverksverdier i avtale der koden er forskjellig fra selve arbeidsforholdet
        for (arbeidsavtale in arbeidsforhold?.arbeidsavtaler.orEmpty()) {
            if (yrke != arbeidsavtale.yrke) {
                arbeidsavtale.yrke = getYrkeTerm(kodeverkConsumer.hentYrke(), arbeidsavtale.yrke, true)
            } else {
                arbeidsavtale.yrke = arbeidsforhold?.yrke
            }
            if (arbeidstidsordning != arbeidsavtale.arbeidstidsordning) {
                arbeidsavtale.arbeidstidsordning = getKodeverksTerm(kodeverkConsumer.hentArbeidstidsordningstyper(), arbeidsavtale.arbeidstidsordning, "Arbeidsforholdstype")
            } else {
                arbeidsavtale.arbeidstidsordning = arbeidsforhold?.arbeidstidsordning
            }
            if (skipsregister != arbeidsavtale.skipsregister) {
                arbeidsavtale.skipsregister = getKodeverksTerm(kodeverkConsumer.hentSkipsregister(), arbeidsavtale.skipsregister, "Skipsregister")
            } else {
                arbeidsavtale.skipsregister = arbeidsforhold?.skipsregister
            }
            if (skipstype != arbeidsavtale.skipstype) {
                arbeidsavtale.skipstype = getKodeverksTerm(kodeverkConsumer.hentSkipstyper(), arbeidsavtale.skipstype, "Skipstype")
            } else {
                arbeidsavtale.skipstype = arbeidsforhold?.skipstype
            }
            if (fartsomraade != arbeidsavtale.fartsomraade) {
                arbeidsavtale.fartsomraade = getKodeverksTerm(kodeverkConsumer.hentFartsomraade(), arbeidsavtale.fartsomraade, "Fartsomraade")
            } else {
                arbeidsavtale.fartsomraade = arbeidsforhold?.fartsomraade
            }
        }
    }

    private fun settInnKodeverksverdierIUtenlandsopphold(utenlandsoppholdDto: ArrayList<UtenlandsoppholdDto>?) {
        for (opphold in utenlandsoppholdDto.orEmpty()) {
            opphold.land = getKodeverksTerm(kodeverkConsumer.hentLand(), opphold.land, "Land")
        }
    }

    private fun settInnKodeverksverdierIPermitteringer(permitteringsDto: ArrayList<PermisjonPermitteringDto>?) {
        for (permittering in permitteringsDto.orEmpty()) {
            permittering.type = getKodeverksTerm(kodeverkConsumer.hentPermisjonstype(), permittering.type, "Land")
        }
    }

    private fun settKodeverkVerdier(arbeidsforhold: ArbeidsforholdDto, yrke: GetKodeverkKoderBetydningerResponse, type: GetKodeverkKoderBetydningerResponse, arbeidstidsordning: GetKodeverkKoderBetydningerResponse, skipsregister: GetKodeverkKoderBetydningerResponse, skipstype: GetKodeverkKoderBetydningerResponse, fartsomraade: GetKodeverkKoderBetydningerResponse) {
        arbeidsforhold.yrke = getYrkeTerm(yrke, arbeidsforhold.yrke, true)
        arbeidsforhold.type = getKodeverksTerm(type, arbeidsforhold.type, "Arbeidsforholdstype")
        arbeidsforhold.arbeidstidsordning = getKodeverksTerm(arbeidstidsordning, arbeidsforhold.arbeidstidsordning, "Arbeidsforholdstype")
        arbeidsforhold.skipsregister = getKodeverksTerm(skipsregister, arbeidsforhold.skipsregister, "Skipsregister")
        arbeidsforhold.skipstype = getKodeverksTerm(skipstype, arbeidsforhold.skipstype, "Skipstype")
        arbeidsforhold.fartsomraade = getKodeverksTerm(fartsomraade, arbeidsforhold.fartsomraade, "Fartsomraade")
    }

    private fun hentOpplysningspliktigOrgNavn(arbeidsforhold: Arbeidsforhold): String? {
        val orgnr = arbeidsforhold.opplysningspliktig?.organisasjonsnummer
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon: EregOrganisasjon? = eregConsumer.hentOrgnavn(orgnr, arbeidsforhold.ansettelsesperiode?.periode?.tom)
            if (organisasjon != null) {
                return concatenateNavn(organisasjon.navn)
            }
        }
        return orgnr
    }

    private fun hentArbGiverOrgNavn(arbeidsforhold: Arbeidsforhold): String? {
        val orgnr = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon: EregOrganisasjon? = eregConsumer.hentOrgnavn(orgnr, arbeidsforhold.ansettelsesperiode?.periode?.tom)
            if (organisasjon != null) {
                return concatenateNavn(organisasjon.navn)
            }
        }
        return orgnr
    }

    private fun getYrkeTerm(yrke: GetKodeverkKoderBetydningerResponse, inbound: String?, inkluderYrkeskode: Boolean): String? {
        try {
            if (!inbound.isNullOrEmpty() && !yrke.betydninger.getValue(inbound).isEmpty()) {
                val yrkesterm = yrke.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
                if (yrkesterm.isNullOrEmpty() || !inkluderYrkeskode)
                    return yrkesterm
                else
                    return yrkesterm + " (Yrkeskode: " + inbound + ")"
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Element not found in Yrke: " + inbound)
        }
        return inbound
    }

    private fun getKodeverksTerm(kodeverk: GetKodeverkKoderBetydningerResponse, inbound: String?, type: String): String? {
        try {
            if (!inbound.isNullOrEmpty() && !kodeverk.betydninger.getValue(inbound).isEmpty()) {
                return kodeverk.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Oppslag på kodeverkstype ".plus(type).plus(" gav ingen treff for verdi ").plus(inbound))
        }
        return inbound
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
