package no.nav.arbeidsforhold

import no.nav.arbeidsforhold.config.ArbeidsforholdConsumer
import no.nav.arbeidsforhold.config.EregConsumer
import no.nav.arbeidsforhold.sts.STSConsumer
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArbeidsforholdService @Autowired constructor(
        private var arbeidsforholdConsumer: ArbeidsforholdConsumer,
        private var stsConsumer: STSConsumer,
        private var eregConsumer: EregConsumer

) {

    private val log = LoggerFactory.getLogger(ArbeidsforholdService::class.java)
    private val tokenbodyindex = 17
    private val tokenbodyend = 42

    fun hentFSSToken(): String {
        val fssToken = stsConsumer.fssToken
        val strippedToken = fssToken.substring(tokenbodyindex, fssToken.length - tokenbodyend)
        log.warn("TOKEN er" + strippedToken)
        return strippedToken
    }

    fun hentArbeidsforhold(fodselsnr: String, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr, fssToken)
        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (af in inbound) {
            //    val arbgivnavn = eregConsumer.hentOrgnavn(af.arbeidsgiver?.organisasjonsnummer).redigertnavn
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(af, ""))
        }
        return arbeidsforholdDtos
    }

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)
        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (af in inbound) {
            //      val arbgivnavn = eregConsumer.hentOrgnavn(af.arbeidsgiver?.organisasjonsnummer).redigertnavn
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(af, ""))
        }
        return arbeidsforholdDtos

    }
}