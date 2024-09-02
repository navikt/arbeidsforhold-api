package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto

object EnkeltArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val arbeidsavtaler = ArbeidsavtaleTransformer.toOutboundArray(inbound.ansettelsesdetaljer, true)
        val gyldigArbeidsavtale = arbeidsavtaler.firstOrNull { it.gyldighetsperiode?.periodeTil == null }

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
            arbeidsavtaler = arbeidsavtaler.takeIf { inbound.ansettelsesdetaljer.size > 1 } ?: emptyList(),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(
                inbound.permitteringer,
                inbound.permisjoner
            ),
            ansettelsesform = gyldigArbeidsavtale?.ansettelsesform,
            antallTimerPrUke = gyldigArbeidsavtale?.antallTimerPrUke,
            stillingsprosent = gyldigArbeidsavtale?.stillingsprosent,
            arbeidstidsordning = gyldigArbeidsavtale?.arbeidstidsordning,
            sisteStillingsendring = gyldigArbeidsavtale?.sisteStillingsendring,
            sisteLoennsendring = gyldigArbeidsavtale?.sisteLoennsendring,
            yrke = gyldigArbeidsavtale?.yrke,
            fartsomraade = gyldigArbeidsavtale?.fartsomraade,
            skipsregister = gyldigArbeidsavtale?.skipsregister,
            skipstype = gyldigArbeidsavtale?.skipstype,
            antallTimerForTimelonnet = AntallTimerForTimeloennetTransformer.toOutboundArray(inbound.timerMedTimeloenn)
        )
    }
}

