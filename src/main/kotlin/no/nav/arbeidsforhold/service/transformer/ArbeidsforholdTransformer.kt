package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto

object ArbeidsforholdTransformer {

    fun toOutbound(inbound: Arbeidsforhold, arbgivnavn: String?, opplarbgivnavn: String?, yrke: String?) =
        ArbeidsforholdDto(

            navArbeidsforholdId = inbound.navArbeidsforholdId,
            eksternArbeidsforholdId = inbound.id,
            yrke = yrke,
            arbeidsgiver = ArbeidsgiverTransformer.toOutbound(inbound.arbeidssted, arbgivnavn),
            opplysningspliktigarbeidsgiver = ArbeidsgiverTransformer.toOutbound(
                inbound.opplysningspliktig,
                opplarbgivnavn
            ),
            ansettelsesperiode = AnsettelsesperiodeTransformer.toOutbound(inbound.ansettelsesperiode),
            utenlandsopphold = UtenlandsoppholdTransformer.toOutboundArray(inbound.utenlandsopphold),
            permisjonPermittering = PermisjonPermitteringTransformer.toOutboundArray(inbound.permitteringer, inbound.permisjoner)

        )
}
