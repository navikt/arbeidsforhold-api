package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.domain.Arbeidsavtale
import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.arbeidsforhold.dto.transformer.EnkeltArbeidsforholdTransformer
import no.nav.arbeidsforhold.kodeverk.Kodeverk
import no.nav.arbeidsforhold.sts.STSConsumer
import no.nav.ereg.Navn
import no.nav.kodeverk.KodeverkConsumer
import no.nav.kodeverk.api.GetKodeverkKoderBetydningerResponse
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
    private var kodeverk = Kodeverk()

    fun hentFSSToken(): String {
        val fssToken = stsConsumer.fssToken
        Thread.sleep(3000)
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
        val arbeidstidsordning = kodeverkConsumer.hentArbeidstidstyper(arbeidsforholdDto.arbeidstidsOrdning)
        getTerms(yrke, arbeidstidsordning, arbeidsforholdDto)
        return arbeidsforholdDto
    }

    private fun concatenateNavn(navn: Navn?): String {
        var orgnavn = ""
        if (!navn?.navnelinje1.isNullOrEmpty()) orgnavn += navn?.navnelinje1.orEmpty()
        if (!navn?.navnelinje2.isNullOrEmpty()) orgnavn += navn?.navnelinje2.orEmpty()
        if (!navn?.navnelinje3.isNullOrEmpty()) orgnavn += navn?.navnelinje3.orEmpty()
        if (!navn?.navnelinje4.isNullOrEmpty()) orgnavn += navn?.navnelinje4.orEmpty()
        if (!navn?.navnelinje5.isNullOrEmpty()) orgnavn += navn?.navnelinje5.orEmpty()

        return orgnavn
    }

    private fun getTerms(yrke: GetKodeverkKoderBetydningerResponse, arbeidstidsordning: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto) {
        getYrkeTerm(yrke, inbound)
        getArbeidstidsordningTerm(arbeidstidsordning, inbound)
    }

    private fun getYrkeTerm(yrke: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto) {
        try {
            if (!inbound.yrke.isNullOrEmpty() && !yrke.betydninger.getValue(inbound.yrke).isEmpty()) {
                kodeverk.yrketerm = yrke.betydninger.getValue(inbound.yrke)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            kodeverk.yrketerm = inbound.yrke
            log.warn("Element not found in Yrke: " + inbound.yrke)
        }

    }

    private fun getArbeidstidsordningTerm(arbeidstidsordning: GetKodeverkKoderBetydningerResponse, inbound: ArbeidsforholdDto) {
        try {
            if (!inbound.arbeidstidsOrdning.isNullOrEmpty() && !arbeidstidsordning.betydninger.getValue(inbound.arbeidstidsOrdning).isEmpty()) {
                kodeverk.arbeidstidsordningterm = arbeidstidsordning.betydninger.getValue(inbound.arbeidstidsOrdning)[0]?.beskrivelser?.getValue(kodeverkspraak)?.term
            }
        } catch (nse: NoSuchElementException) {
            kodeverk.arbeidstidsordningterm = inbound.arbeidstidsOrdning
            log.warn("Element not found in Arbeidstidsording: " + inbound.arbeidstidsOrdning)
        }

    }

}