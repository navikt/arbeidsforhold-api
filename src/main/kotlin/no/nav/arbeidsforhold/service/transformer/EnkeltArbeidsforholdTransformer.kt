package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto

object EnkeltArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val gyldigarbeidsavtale = gyldigArbeidsavtale(ArbeidsavtaleTransformer.toOutboundArray(inbound.ansettelsesdetaljer, true))

        return ArbeidsforholdDto(
            navArbeidsforholdId = inbound.navArbeidsforholdId,
            eksternArbeidsforholdId = inbound.id,
            type = inbound.type?.beskrivelse,
            sistBekreftet = inbound.sistBekreftet,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidssted, arbgivnavn),
            opplysningspliktigarbeidsgiver = ArbeidsgiverTransformer.toOutbound(
                inbound.opplysningspliktig,
                opplarbgivnavn
            ),
            ansettelsesperiode = AnsettelsesperiodeTransformer.toOutbound(inbound.ansettelsesperiode),
            arbeidsavtaler = if (inbound.ansettelsesdetaljer?.size != 1) {
                ArbeidsavtaleTransformer.toOutboundArray(inbound.ansettelsesdetaljer, true)
            } else {
                ArrayList()
            },
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permitteringer, inbound.permisjoner),
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
            antallTimerForTimelonnet = AntallTimerForTimeloennetTransformer.toOutboundArray(inbound.timerMedTimeloenn)
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

