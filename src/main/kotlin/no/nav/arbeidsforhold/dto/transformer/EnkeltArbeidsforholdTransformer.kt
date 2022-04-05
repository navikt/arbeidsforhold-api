package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto

object EnkeltArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val gyldigarbeidsavtale = gyldigArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler))

        return ArbeidsforholdDto(
            navArbeidsforholdId = inbound.navArbeidsforholdId,
            eksternArbeidsforholdId = inbound.arbeidsforholdId,
            type = inbound.type,
            sistBekreftet = inbound.sistBekreftet,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
            opplysningspliktigarbeidsgiver = ArbeidsgiverTransformer.toOutbound(
                inbound.opplysningspliktig,
                opplarbgivnavn
            ),
            ansettelsesperiode = AnsettelsesperiodeTransformer.toOutbound(inbound.ansettelsesperiode),
            arbeidsavtaler = if (inbound.arbeidsavtaler?.size != 1) {
                ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler)
            } else {
                ArrayList()
            },
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permisjonPermitteringer),
            ansettelsesform = gyldigarbeidsavtale?.ansettelsesform,
            antallTimerPrUke = gyldigarbeidsavtale?.antallTimerPrUke,
            stillingsprosent = gyldigarbeidsavtale?.stillingsprosent,
            arbeidstidsordning = gyldigarbeidsavtale?.arbeidstidsordning,
            sisteStillingsendring = gyldigarbeidsavtale?.sisteStillingsendring,
            sisteLoennsendring = gyldigarbeidsavtale?.sisteLoennsendring,
            yrke = gyldigarbeidsavtale?.yrke,
            fartsomraade = gyldigarbeidsavtale?.fartsomraade,
            skipsregister = gyldigarbeidsavtale?.skipsregister,
            skipstype = gyldigarbeidsavtale?.skipstype,
            antallTimerForTimelonnet = AntallTimerForTimeloennetTransformer.toOutboundArray(inbound.antallTimerForTimeloennet)
        )
    }

    private fun gyldigArbeidsavtale(inbound: List<ArbeidsavtaleDto>): ArbeidsavtaleDto? {
        for (arbeidsavtale in inbound) {
            if (arbeidsavtale.gyldighetsperiode?.periodeTil == null) {
                return arbeidsavtale
            }
        }
        return null
    }

}

