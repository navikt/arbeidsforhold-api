package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsforhold
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.OpplysningspliktigArbeidsgiverTransformer

object EnkeltArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val gyldigarbeidsavtale = gyldigArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler))
        return ArbeidsforholdDto(
                navArbeidsforholdId = inbound.navArbeidsforholdId,
                type = inbound.type,
                sistBekreftet = inbound.sistBekreftet,
                arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidsgiver, arbgivnavn),
                opplysningspliktigarbeidsgiver = OpplysningspliktigArbeidsgiverTransformer.toOutbound(inbound.opplysningspliktig, opplarbgivnavn),
                ansettelsesperiode = PeriodeTransformer.toOutboundfromAnsettelsesperiode(inbound.ansettelsesperiode),
                arbeidsavtaler = ArbeidsavtaleTransformer.toOutboundArray(inbound.arbeidsavtaler),
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

