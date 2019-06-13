package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.dto.outbound.PermisjonPermitteringDto
import no.nav.arbeidsforhold.dto.outbound.UtenlandsoppholdDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.dto.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.services.sts.STSConsumer
import no.nav.ereg.Navn
import no.nav.arbeidsforhold.services.kodeverk.KodeverkConsumer
import no.nav.arbeidsforhold.services.kodeverk.api.GetKodeverkKoderBetydningerResponse
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

            var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
            var opplarbgivnavn = arbeidsforhold.opplysningspliktig?.organisasjonsnummer
            arbgivnavn = hentArbGiverOrgNavn(arbeidsforhold, arbgivnavn)
            opplarbgivnavn = hentOpplysningspliktigOrgNavn(arbeidsforhold, opplarbgivnavn)
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn))
        }
        return arbeidsforholdDtos
    }



    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String?): ArbeidsforholdDto {
        val arbeidsforhold = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)



        var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
        var opplarbgivnavn = arbeidsforhold.opplysningspliktig?.organisasjonsnummer
        arbgivnavn = hentEttarbforholdOrgnavn(arbeidsforhold, arbgivnavn)
        opplarbgivnavn = hentEttarbforholdOpplysningspliktig(arbeidsforhold, opplarbgivnavn)
        val arbeidsforholdDto = EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn)

        settInnKodeverksverdierIUtenlandsopphold(arbeidsforholdDto.utenlandsopphold)

        settInnKodeverksverdierIArbeidsavtale(arbeidsforholdDto.arbeidsavtaler)

        settInnKodeverksverdierIPermitteringer(arbeidsforholdDto.permisjonPermittering)

        val yrke = kodeverkConsumer.hentYrke(arbeidsforholdDto.yrke)
        val type = kodeverkConsumer.hentArbeidsforholdstyper(arbeidsforholdDto.type)
        val arbeidstidsordning = kodeverkConsumer.hentArbeidstidsordningstyper(arbeidsforholdDto.arbeidstidsordning)
        val skipstype = kodeverkConsumer.hentSkipstyper(arbeidsforholdDto.skipstype)
        val skipsregister = kodeverkConsumer.hentSkipsregister(arbeidsforholdDto.skipsregister)
        val fartsomraade = kodeverkConsumer.hentFartsomraade(arbeidsforholdDto.fartsomraade)

        settKodeverkVerdier(arbeidsforholdDto, yrke, type, arbeidstidsordning, skipsregister, skipstype, fartsomraade)
        return arbeidsforholdDto
    }

    private fun settInnKodeverksverdierIArbeidsavtale(arbeidsavtaleDto: ArrayList<ArbeidsavtaleDto>?) {
        for (arbeidsavtale in arbeidsavtaleDto.orEmpty()) {
            arbeidsavtale.yrke = getYrkeTerm(kodeverkConsumer.hentYrke(arbeidsavtale.yrke), arbeidsavtale.yrke)
            arbeidsavtale.arbeidstidsordning = getArbeidstidsordningTerm(kodeverkConsumer.hentArbeidstidsordningstyper(arbeidsavtale.arbeidstidsordning), arbeidsavtale.arbeidstidsordning)
            arbeidsavtale.skipsregister = getSkipsregisterTerm(kodeverkConsumer.hentSkipsregister(arbeidsavtale.skipsregister),  arbeidsavtale.skipsregister)
            arbeidsavtale.skipstype = getSkipstypeTerm(kodeverkConsumer.hentSkipstyper(arbeidsavtale.skipstype),  arbeidsavtale.skipstype)
            arbeidsavtale.fartsomraade = getFartsomraadeTerm(kodeverkConsumer.hentFartsomraade(arbeidsavtale.fartsomraade),  arbeidsavtale.fartsomraade)
        }
    }

    private fun settInnKodeverksverdierIUtenlandsopphold(utenlandsoppholdDto: ArrayList<UtenlandsoppholdDto>?) {
        for (opphold in utenlandsoppholdDto.orEmpty()) {
            opphold.land = getLandTerm(kodeverkConsumer.hentLand(opphold.land), opphold.land)
        }
    }

    private fun settInnKodeverksverdierIPermitteringer(permitteringsDto: ArrayList<PermisjonPermitteringDto>?) {
        for (permittering in permitteringsDto.orEmpty()) {
            permittering.type = getLandTerm(kodeverkConsumer.hentPermisjonstype(permittering.type), permittering.type)
        }
    }

    private fun settKodeverkVerdier(arbeidsforhold: ArbeidsforholdDto, yrke: GetKodeverkKoderBetydningerResponse, type: GetKodeverkKoderBetydningerResponse, arbeidstidsordning: GetKodeverkKoderBetydningerResponse, skipsregister: GetKodeverkKoderBetydningerResponse, skipstype: GetKodeverkKoderBetydningerResponse, fartsomraade: GetKodeverkKoderBetydningerResponse) {
        arbeidsforhold.yrke = getYrkeTerm(yrke, arbeidsforhold.yrke)
        arbeidsforhold.type = getArbeidsforholdstypeTerm(type, arbeidsforhold.type)
        arbeidsforhold.arbeidstidsordning = getArbeidstidsordningTerm(arbeidstidsordning, arbeidsforhold.arbeidstidsordning)
        arbeidsforhold.skipsregister = getSkipsregisterTerm(skipsregister, arbeidsforhold.skipsregister)
        arbeidsforhold.skipstype = getSkipstypeTerm(skipstype, arbeidsforhold.skipstype)
        arbeidsforhold.fartsomraade = getFartsomraadeTerm(fartsomraade, arbeidsforhold.fartsomraade)
    }

    private fun hentEttarbforholdOpplysningspliktig(arbeidsforhold: Arbeidsforhold, opplarbgivnavn: String?): String? {
        var opplarbgivnavn1 = opplarbgivnavn
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer, arbeidsforhold.ansettelsesperiode?.periode?.tom)
            val navn = organisasjon.navn
            opplarbgivnavn1 = concatenateNavn(navn)
        }
        return opplarbgivnavn1
    }

    private fun hentEttarbforholdOrgnavn(arbeidsforhold: Arbeidsforhold, arbgivnavn: String?): String? {
        var arbgivnavn1 = arbgivnavn
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer,  arbeidsforhold.ansettelsesperiode?.periode?.tom)
            val navn = organisasjon.navn
            arbgivnavn1 = concatenateNavn(navn)
        }
        return arbgivnavn1
    }


    private fun hentOpplysningspliktigOrgNavn(arbeidsforhold: Arbeidsforhold, opplarbgivnavn: String?): String? {
        var opplarbgivnavn1 = opplarbgivnavn
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer,  arbeidsforhold.ansettelsesperiode?.periode?.tom)
            val navn = organisasjon.navn
            opplarbgivnavn1 = concatenateNavn(navn)
        }
        return opplarbgivnavn1
    }

    private fun hentArbGiverOrgNavn(arbeidsforhold: Arbeidsforhold, arbgivnavn: String?): String? {
        var arbgivnavn1 = arbgivnavn
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer,  arbeidsforhold.ansettelsesperiode?.periode?.tom)
            val navn = organisasjon.navn
            arbgivnavn1 = concatenateNavn(navn)
        }
        return arbgivnavn1
    }


   /* private fun geTerm(term: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!term.betydninger.getValue(term).isEmpty()) {
                return term.betydninger.getValue(term.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Element not found in Yrke: " + inbound.yrke)

        }
        return inbound.yrke
    }*/

    private fun getYrkeTerm(yrke: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !yrke.betydninger.getValue(inbound).isEmpty()) {
                return yrke.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Element not found in Yrke: " + inbound)

        }
        return inbound
    }

    private fun getArbeidsforholdstypeTerm(type: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !type.betydninger.getValue(inbound).isEmpty()) {
                return type.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Arbeidsforholdstype: " + inbound)
        }
        return inbound
    }

    private fun getArbeidstidsordningTerm(ordning: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !ordning.betydninger.getValue(inbound).isEmpty()) {
                return ordning.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Arbeidsforholdstype: " + inbound)
        }
        return inbound
    }

    private fun getSkipsregisterTerm(skipsregister: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !skipsregister.betydninger.getValue(inbound).isEmpty()) {
                return skipsregister.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Skipsregister: " + inbound)
        }
        return inbound
    }

    private fun getSkipstypeTerm(skipstype: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !skipstype.betydninger.getValue(inbound).isEmpty()) {
                return skipstype.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Skipstype: " + inbound)
        }
        return inbound
    }

    private fun getFartsomraadeTerm(fartsomraade: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !fartsomraade.betydninger.getValue(inbound).isEmpty()) {
                return fartsomraade.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Fartsomraade: " + inbound)
        }
        return inbound
    }

    private fun getLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: String?): String? {
        try {
            if (!inbound.isNullOrEmpty() && !land.betydninger.getValue(inbound).isEmpty()) {
                return land.betydninger.getValue(inbound)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Land: " + inbound)
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