package no.nav.arbeidsforhold.service

import no.nav.arbeidsforhold.consumer.aareg.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesperiode
import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsgiver
import no.nav.arbeidsforhold.consumer.ereg.EregConsumer
import no.nav.arbeidsforhold.consumer.ereg.domain.EregOrganisasjon
import no.nav.arbeidsforhold.consumer.ereg.domain.Navn
import no.nav.arbeidsforhold.consumer.kodeverk.KodeverkConsumer
import no.nav.arbeidsforhold.consumer.kodeverk.domain.GetKodeverkKoderBetydningerResponse
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.service.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer
import no.nav.arbeidsforhold.service.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.service.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.util.DtoUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArbeidsforholdService @Autowired constructor(
    private var arbeidsforholdConsumer: ArbeidsforholdConsumer,
    private var eregConsumer: EregConsumer,
    private var kodeverkConsumer: KodeverkConsumer
) {

    private val log = LoggerFactory.getLogger(ArbeidsforholdService::class.java)
    private val organisasjon = "Organisasjon"
    private val kodeverkspraak = "nb"

    fun hentArbeidsforhold(fodselsnr: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr)
        val arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {
            val yrke = DtoUtils.hentYrkeForSisteArbeidsavtale(
                ArbeidsavtaleTransformer.toOutboundArray(arbeidsforhold.arbeidsavtaler)
            )?.run { getYrkeTerm(kodeverkConsumer.hentYrke(), this, false) }
            val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.arbeidsgiver, arbeidsforhold.ansettelsesperiode)
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

        val arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.arbeidsgiver, arbeidsforhold.ansettelsesperiode)
        val opplarbgivnavn = hentArbGiverOrgNavn(arbeidsforhold.opplysningspliktig, arbeidsforhold.ansettelsesperiode)
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
        val sluttaarsak = kodeverkConsumer.hentSluttAarsak()

        settKodeverkVerdier(
            arbeidsforholdDto,
            yrke,
            type,
            arbeidstidsordning,
            skipsregister,
            skipstype,
            fartsomraade,
            sluttaarsak
        )
        settInnKodeverksverdierIUtenlandsopphold(arbeidsforholdDto.utenlandsopphold)
        settInnKodeverksverdierIArbeidsavtale(
            arbeidsforholdDto,
            yrkeskode,
            arbeidstidsordningkode,
            skipstypekode,
            skipsregisterkode,
            fartsomraadekode
        )
        settInnKodeverksverdierIPermitteringer(arbeidsforholdDto.permisjonPermittering)

        return arbeidsforholdDto
    }

    private fun settInnKodeverksverdierIArbeidsavtale(
        arbeidsforhold: ArbeidsforholdDto?,
        yrke: String?,
        arbeidstidsordning: String?,
        skipsregister: String?,
        skipstype: String?,
        fartsomraade: String?
    ) {
        //Setter inn kodeverksverdier i avtale der koden er forskjellig fra selve arbeidsforholdet
        for (arbeidsavtale in arbeidsforhold?.arbeidsavtaler.orEmpty()) {
            if (yrke != arbeidsavtale.yrke) {
                arbeidsavtale.yrke = getYrkeTerm(kodeverkConsumer.hentYrke(), arbeidsavtale.yrke, true)
            } else {
                arbeidsavtale.yrke = arbeidsforhold?.yrke
            }
            if (arbeidstidsordning != arbeidsavtale.arbeidstidsordning) {
                arbeidsavtale.arbeidstidsordning = getKodeverksTerm(
                    kodeverkConsumer.hentArbeidstidsordningstyper(),
                    arbeidsavtale.arbeidstidsordning,
                    "Arbeidsforholdstype"
                )
            } else {
                arbeidsavtale.arbeidstidsordning = arbeidsforhold?.arbeidstidsordning
            }
            if (skipsregister != arbeidsavtale.skipsregister) {
                arbeidsavtale.skipsregister =
                    getKodeverksTerm(kodeverkConsumer.hentSkipsregister(), arbeidsavtale.skipsregister, "Skipsregister")
            } else {
                arbeidsavtale.skipsregister = arbeidsforhold?.skipsregister
            }
            if (skipstype != arbeidsavtale.skipstype) {
                arbeidsavtale.skipstype =
                    getKodeverksTerm(kodeverkConsumer.hentSkipstyper(), arbeidsavtale.skipstype, "Skipstype")
            } else {
                arbeidsavtale.skipstype = arbeidsforhold?.skipstype
            }
            if (fartsomraade != arbeidsavtale.fartsomraade) {
                arbeidsavtale.fartsomraade =
                    getKodeverksTerm(kodeverkConsumer.hentFartsomraade(), arbeidsavtale.fartsomraade, "Fartsomraade")
            } else {
                arbeidsavtale.fartsomraade = arbeidsforhold?.fartsomraade
            }
        }
    }

    private fun settInnKodeverksverdierIUtenlandsopphold(utenlandsoppholdDto: List<UtenlandsoppholdDto>?) {
        for (opphold in utenlandsoppholdDto.orEmpty()) {
            opphold.land = getKodeverksTerm(kodeverkConsumer.hentLand(), opphold.land, "Land")
        }
    }

    private fun settInnKodeverksverdierIPermitteringer(permitteringsDto: List<PermisjonPermitteringDto>?) {
        for (permittering in permitteringsDto.orEmpty()) {
            permittering.type = getKodeverksTerm(kodeverkConsumer.hentPermisjonstype(), permittering.type, "Land")
        }
    }

    private fun settKodeverkVerdier(
        arbeidsforhold: ArbeidsforholdDto,
        yrke: GetKodeverkKoderBetydningerResponse,
        type: GetKodeverkKoderBetydningerResponse,
        arbeidstidsordning: GetKodeverkKoderBetydningerResponse,
        skipsregister: GetKodeverkKoderBetydningerResponse,
        skipstype: GetKodeverkKoderBetydningerResponse,
        fartsomraade: GetKodeverkKoderBetydningerResponse,
        sluttaarsak: GetKodeverkKoderBetydningerResponse
    ) {
        arbeidsforhold.yrke = getYrkeTerm(yrke, arbeidsforhold.yrke, true)
        arbeidsforhold.type = getKodeverksTerm(type, arbeidsforhold.type, "Arbeidsforholdstype")
        arbeidsforhold.arbeidstidsordning =
            getKodeverksTerm(arbeidstidsordning, arbeidsforhold.arbeidstidsordning, "Arbeidsforholdstype")
        arbeidsforhold.skipsregister = getKodeverksTerm(skipsregister, arbeidsforhold.skipsregister, "Skipsregister")
        arbeidsforhold.skipstype = getKodeverksTerm(skipstype, arbeidsforhold.skipstype, "Skipstype")
        arbeidsforhold.fartsomraade = getKodeverksTerm(fartsomraade, arbeidsforhold.fartsomraade, "Fartsomraade")
        arbeidsforhold.ansettelsesperiode?.sluttaarsak =
            getKodeverksTerm(sluttaarsak, arbeidsforhold.ansettelsesperiode?.sluttaarsak, "Sluttaarsak")
    }

    private fun hentArbGiverOrgNavn(arbeidsgiver: Arbeidsgiver?, ansettelsesperiode: Ansettelsesperiode?): String? {
        val orgnr = arbeidsgiver?.organisasjonsnummer
        if (arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon: EregOrganisasjon? = eregConsumer.hentOrgnavn(orgnr, ansettelsesperiode?.periode?.tom)
            if (organisasjon != null) {
                return concatenateNavn(organisasjon.navn)
            }
        }
        return orgnr
    }

    private fun getYrkeTerm(
        yrke: GetKodeverkKoderBetydningerResponse,
        inbound: String?,
        inkluderYrkeskode: Boolean
    ): String? {
        try {
            if (!inbound.isNullOrEmpty() && yrke.betydninger.getValue(inbound).isNotEmpty()) {
                val yrkesterm = yrke.betydninger.getValue(inbound)[0].beskrivelser?.getValue(kodeverkspraak)?.term
                return if (yrkesterm.isNullOrEmpty() || !inkluderYrkeskode)
                    yrkesterm
                else
                    "$yrkesterm (Yrkeskode: $inbound)"
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Element not found in Yrke: $inbound")
        }
        return inbound
    }

    private fun getKodeverksTerm(
        kodeverk: GetKodeverkKoderBetydningerResponse,
        inbound: String?,
        type: String
    ): String? {
        try {
            if (!inbound.isNullOrEmpty() && kodeverk.betydninger.getValue(inbound).isNotEmpty()) {
                return kodeverk.betydninger.getValue(inbound)[0].beskrivelser?.getValue(kodeverkspraak)?.term
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
