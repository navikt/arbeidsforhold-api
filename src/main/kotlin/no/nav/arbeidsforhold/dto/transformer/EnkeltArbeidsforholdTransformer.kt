package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsavtale
import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.services.ArbeidsforholdService
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.OpplysningspliktigArbeidsgiverTransformer
import org.slf4j.LoggerFactory

object EnkeltArbeidsforholdTransformer {
    private val log = LoggerFactory.getLogger(EnkeltArbeidsforholdTransformer::class.java)
    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val gyldigarbeidsavtale = gyldigArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler))

        return ArbeidsforholdDto(
                navArbeidsforholdId = inbound.navArbeidsforholdId,
                eksternArbeidsforholdId = inbound.arbeidsforholdId,
                type = inbound.type,
                sistBekreftet = inbound.sistBekreftet,
                arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
                opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
                ansettelsesperiode = AnsettelsesperiodeTransformer.toOutbound(inbound.ansettelsesperiode),
                arbeidsavtaler = if (inbound.arbeidsavtaler?.size != 1) {
                    ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler)
                } else {
                    ArrayList<ArbeidsavtaleDto>()
                },
                utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
                permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer),
                antallTimerPrUke = gyldigarbeidsavtale?.antallTimerPrUke,
                stillingsprosent = gyldigarbeidsavtale?.stillingsprosent,
                arbeidstidsordning = gyldigarbeidsavtale?.arbeidstidsordning,
                sisteStillingsEndring = gyldigarbeidsavtale?.sisteStillingsendring,
                sisteLoennsEndring = gyldigarbeidsavtale?.sisteLoennsendring,
                yrke = gyldigarbeidsavtale?.yrke,
                fartsomraade = gyldigarbeidsavtale?.fartsomraade,
                skipsregister = gyldigarbeidsavtale?.skipsregister,
                skipstype = gyldigarbeidsavtale?.skipstype,
                antallTimerForTimelonnet = AntallTimerForTimeloennetTransformer.toOutboundArray(inbound.antallTimerForTimeloennet)
        )
    }

    fun gyldigArbeidsavtale(inbound: List<ArbeidsavtaleDto>): ArbeidsavtaleDto? {
        for (arbeidsavtale in inbound) {
            if (arbeidsavtale.gyldighetsperiode?.periodeTil == null) {
                return arbeidsavtale
            }
        }
        return null
    }

}

