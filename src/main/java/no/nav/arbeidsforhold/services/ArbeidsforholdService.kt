package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
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
        var utenlandsoppholdDto = arbeidsforholdDto.utenlandsopphold
        for (oppholdDto in utenlandsoppholdDto)
        {
            val land = kodeverkConsumer.hentLand(oppholdDto.land)
            oppholdDto.land = getLandTerm(land, oppholdDto)
        }

        val yrke = kodeverkConsumer.hentYrke(arbeidsforholdDto.yrke)
        val type = kodeverkConsumer.hentArbeidsforholdstyper(arbeidsforholdDto.type)
        val arbeidstidsordning = kodeverkConsumer.hentArbeidstidsordningstyper(arbeidsforholdDto.arbeidstidsordning)
        val skipstype = kodeverkConsumer.hentSkipstyper(arbeidsforholdDto.skipstype)
        val skipsregister = kodeverkConsumer.hentSkipsregister(arbeidsforholdDto.skipsregister)
        val fartsomraade = kodeverkConsumer.hentFartsomraade(arbeidsforholdDto.fartsomraade)

        settKodeverkVerdier(arbeidsforholdDto, yrke, type, arbeidstidsordning, skipsregister, skipstype, fartsomraade)
        return arbeidsforholdDto
    }

    private fun settKodeverkVerdier(arbeidsforholdDto: ArbeidsforholdDto, yrke: GetKodeverkKoderBetydningerResponse, type: GetKodeverkKoderBetydningerResponse, arbeidstidsordning: GetKodeverkKoderBetydningerResponse, skipsregister: GetKodeverkKoderBetydningerResponse, skipstype: GetKodeverkKoderBetydningerResponse, fartsomraade: GetKodeverkKoderBetydningerResponse) {
        arbeidsforholdDto.yrke = getYrkeTerm(yrke, arbeidsforholdDto)
        arbeidsforholdDto.type = getArbeidsforholdstypeTerm(type, arbeidsforholdDto)
        arbeidsforholdDto.arbeidstidsordning = getArbeidstidsordningTerm(arbeidstidsordning, arbeidsforholdDto)
        arbeidsforholdDto.skipsregister = getSkipsregisterTerm(skipsregister, arbeidsforholdDto)
        arbeidsforholdDto.skipstype = getSkipstypeTerm(skipstype, arbeidsforholdDto)
        arbeidsforholdDto.fartsomraade = getFartsomraadeTerm(fartsomraade, arbeidsforholdDto)
    }

    private fun hentEttarbforholdOpplysningspliktig(arbeidsforhold: Arbeidsforhold, opplarbgivnavn: String?): String? {
        var opplarbgivnavn1 = opplarbgivnavn
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer)
            val navn = organisasjon.navn
            opplarbgivnavn1 = concatenateNavn(navn)
        }
        return opplarbgivnavn1
    }

    private fun hentEttarbforholdOrgnavn(arbeidsforhold: Arbeidsforhold, arbgivnavn: String?): String? {
        var arbgivnavn1 = arbgivnavn
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
            val navn = organisasjon.navn
            arbgivnavn1 = concatenateNavn(navn)
        }
        return arbgivnavn1
    }


    private fun hentOpplysningspliktigOrgNavn(arbeidsforhold: Arbeidsforhold, opplarbgivnavn: String?): String? {
        var opplarbgivnavn1 = opplarbgivnavn
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer)
            val navn = organisasjon.navn
            opplarbgivnavn1 = concatenateNavn(navn)
        }
        return opplarbgivnavn1
    }

    private fun hentArbGiverOrgNavn(arbeidsforhold: Arbeidsforhold, arbgivnavn: String?): String? {
        var arbgivnavn1 = arbgivnavn
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
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

    private fun getYrkeTerm(yrke: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.yrke.isNullOrEmpty() && !yrke.betydninger.getValue(inbound.yrke).isEmpty()) {
                return yrke.betydninger.getValue(inbound.yrke)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            log.warn("Element not found in Yrke: " + inbound.yrke)

        }
        return inbound.yrke
    }

    private fun getArbeidsforholdstypeTerm(type: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.type.isNullOrEmpty() && !type.betydninger.getValue(inbound.type).isEmpty()) {
                return type.betydninger.getValue(inbound.type)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Arbeidsforholdstype: " + inbound.type)
        }
        return inbound.type
    }

    private fun getArbeidstidsordningTerm(ordning: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.arbeidstidsordning.isNullOrEmpty() && !ordning.betydninger.getValue(inbound.arbeidstidsordning).isEmpty()) {
                return ordning.betydninger.getValue(inbound.arbeidstidsordning)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Arbeidsforholdstype: " + inbound.arbeidstidsordning)
        }
        return inbound.arbeidstidsordning
    }

    private fun getSkipsregisterTerm(skipsregister: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.skipsregister.isNullOrEmpty() && !skipsregister.betydninger.getValue(inbound.skipsregister).isEmpty()) {
                return skipsregister.betydninger.getValue(inbound.skipsregister)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Skipsregister: " + inbound.skipsregister)
        }
        return inbound.skipsregister
    }

    private fun getSkipstypeTerm(skipstype: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.skipstype.isNullOrEmpty() && !skipstype.betydninger.getValue(inbound.skipstype).isEmpty()) {
                return skipstype.betydninger.getValue(inbound.skipstype)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Skipstype: " + inbound.skipstype)
        }
        return inbound.skipstype
    }

    private fun getFartsomraadeTerm(fartsomraade: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto): String? {
        try {
            if (!inbound.fartsomraade.isNullOrEmpty() && !fartsomraade.betydninger.getValue(inbound.fartsomraade).isEmpty()) {
                return fartsomraade.betydninger.getValue(inbound.fartsomraade)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Fartsomraade: " + inbound.fartsomraade)
        }
        return inbound.fartsomraade
    }

    private fun getLandTerm(land: GetKodeverkKoderBetydningerResponse, inbound: UtenlandsoppholdDto): String? {
        try {
            if (!inbound.land.isNullOrEmpty() && !land.betydninger.getValue(inbound.land).isEmpty()) {
                return land.betydninger.getValue(inbound.land)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Land: " + inbound.land)
        }
        return inbound.land
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