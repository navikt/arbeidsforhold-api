package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Arbeidsavtale
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto


object ArbeidsavtaleTransformer {

    fun toOutboundArray(inbound: List<Arbeidsavtale>?): List<ArbeidsavtaleDto> {

        val arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            val avtaledto = ArbeidsavtaleDto(
                ansettelsesform = arbeidsavtale.ansettelsesform,
                antallTimerPrUke = arbeidsavtale.antallTimerPrUke,
                arbeidstidsordning = arbeidsavtale.arbeidstidsordning,
                sisteStillingsendring = arbeidsavtale.sistStillingsendring,
                stillingsprosent = arbeidsavtale.stillingsprosent,
                sisteLoennsendring = arbeidsavtale.sistLoennsendring,
                yrke = arbeidsavtale.yrke,
                gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.gyldighetsperiode),
                fartsomraade = arbeidsavtale.fartsomraade,
                skipsregister = arbeidsavtale.skipsregister,
                skipstype = arbeidsavtale.skipstype
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}