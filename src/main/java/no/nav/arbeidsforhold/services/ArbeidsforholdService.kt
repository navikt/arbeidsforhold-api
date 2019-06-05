package no.nav.arbeidsforhold.services

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
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
    private val tokenbodyindex = 17
    private val tokenbodyend = 42
    private val organisasjon = "Organisasjon"
    private val kodeverkspraak = "nb"

    fun hentFSSToken(): String {
        val fssToken = stsConsumer.fssToken

        //Thread.sleep(1000)
        val strippedToken = fssToken.substring(tokenbodyindex, fssToken.length - tokenbodyend)
        return strippedToken
    }

    fun hentArbeidsforhold(fodselsnr: String, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr, fssToken)


        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {

            var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
            var opplarbgivnavn = arbeidsforhold.opplysningspliktig?.organisasjonsnummer
            if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
                val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
                val navn = organisasjon.navn
                arbgivnavn = concatenateNavn(navn)
            }
            if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
                val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer)
                val navn = organisasjon.navn
                opplarbgivnavn = concatenateNavn(navn)
            }
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn))
        }
        return arbeidsforholdDtos
    }

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String): ArbeidsforholdDto {
        val arbeidsforhold = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)


        var arbeidsforholdDto: ArbeidsforholdDto
        var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
        var opplarbgivnavn = arbeidsforhold.opplysningspliktig?.organisasjonsnummer
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
            val navn = organisasjon.navn
            arbgivnavn = concatenateNavn(navn)
        }
        if (arbeidsforhold.opplysningspliktig?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.opplysningspliktig?.organisasjonsnummer)
            val navn = organisasjon.navn
            opplarbgivnavn = concatenateNavn(navn)
        }
        arbeidsforholdDto = EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn, opplarbgivnavn)
        val yrke = kodeverkConsumer.hentYrke(arbeidsforholdDto.yrke)
        val type = kodeverkConsumer.hentArbeidsforholdstyper(arbeidsforholdDto.type)
        val arbeidstidsordning = kodeverkConsumer.hentArbeidsforholdstyper(arbeidsforholdDto.arbeidstidsOrdning)

        arbeidsforholdDto.yrke = getYrkeTerm(yrke, arbeidsforholdDto)
        arbeidsforholdDto.type = getArbeidsforholdstypeTerm(type, arbeidsforholdDto)
        arbeidsforholdDto.arbeidstidsOrdning = getArbeidstidsordningTerm(arbeidstidsordning, arbeidsforholdDto)
        return arbeidsforholdDto
    }

    private fun concatenateNavn(navn: Navn?): String {
        var orgnavn = ""
        if (!navn?.navnelinje1.isNullOrEmpty()) orgnavn += navn?.navnelinje1.orEmpty() + " "
        if (!navn?.navnelinje2.isNullOrEmpty()) orgnavn += navn?.navnelinje2.orEmpty() + " "
        if (!navn?.navnelinje3.isNullOrEmpty()) orgnavn += navn?.navnelinje3.orEmpty() + " "
        if (!navn?.navnelinje4.isNullOrEmpty()) orgnavn += navn?.navnelinje4.orEmpty() + " "
        if (!navn?.navnelinje5.isNullOrEmpty()) orgnavn += navn?.navnelinje5.orEmpty()

        return orgnavn
    }

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
            if (!inbound.arbeidstidsOrdning.isNullOrEmpty() && !ordning.betydninger.getValue(inbound.arbeidstidsOrdning).isEmpty()) {
                return ordning.betydninger.getValue(inbound.arbeidstidsOrdning)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {

            log.warn("Element not found in Arbeidsforholdstype: " + inbound.arbeidstidsOrdning)
        }
        return inbound.arbeidstidsOrdning
    }

}