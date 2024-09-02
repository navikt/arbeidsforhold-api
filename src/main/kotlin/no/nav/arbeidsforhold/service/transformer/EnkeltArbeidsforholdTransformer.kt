package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.service.transformer.AnsettelsesperiodeTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.AntallTimerForTimeloennetTransformer.toOutboundArray
import no.nav.arbeidsforhold.service.transformer.ArbeidsavtaleTransformer.toOutboundArray
import no.nav.arbeidsforhold.service.transformer.ArbeidsgiverTransformer.toOutbound
import no.nav.arbeidsforhold.service.transformer.PermisjonPermitteringTransformer.toOutboundArray
import no.nav.arbeidsforhold.service.transformer.UtenlandsoppholdTransformer.toOutboundArray

object EnkeltArbeidsforholdTransformer {

    fun Arbeidsforhold.toOutbound(arbgivnavn: String?, opplarbgivnavn: String?): ArbeidsforholdDto {

        val arbeidsavtaler = ansettelsesdetaljer.toOutboundArray(true)
        val gyldigArbeidsavtale = arbeidsavtaler.firstOrNull { it.gyldighetsperiode?.periodeTil == null }

        return ArbeidsforholdDto(
            navArbeidsforholdId = navArbeidsforholdId,
            eksternArbeidsforholdId = id,
            type = type?.beskrivelse,
            sistBekreftet = sistBekreftet,
            arbeidsgiver = arbeidssted.toOutbound(arbgivnavn),
            opplysningspliktigarbeidsgiver = opplysningspliktig.toOutbound(
                opplarbgivnavn
            ),
            ansettelsesperiode = ansettelsesperiode.toOutbound(),
            arbeidsavtaler = arbeidsavtaler.takeIf { ansettelsesdetaljer.size > 1 } ?: emptyList(),
            utenlandsopphold = utenlandsopphold.toOutboundArray(),
            permisjonPermittering = permisjoner.plus(permitteringer).toOutboundArray(),
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
            antallTimerForTimelonnet = timerMedTimeloenn.toOutboundArray()
        )
    }
}

