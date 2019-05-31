package no.nav.arbeidsforhold.dto.transformer

import no.nav.arbeidsforhold.domain.Arbeidsavtale
import no.nav.arbeidsforhold.dto.outbound.ArbeidsavtaleDto
import no.nav.arbeidsforhold.services.kodeverk.Kodeverk


object ArbeidsavtaleTransformer {

    fun toOutboundArray(inbound: Array<Arbeidsavtale>?): ArrayList<ArbeidsavtaleDto> {

        var arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            var avtaledto = ArbeidsavtaleDto(
                    antallTimerPrUke = arbeidsavtale.antallTimerPrUke,
                    arbeidstidsOrdning = arbeidsavtale.arbeidstidsordning,
                    sisteStillingsendring = arbeidsavtale.sistStillingsendring,
                    stillingsprosent = arbeidsavtale.stillingsprosent,
                    sisteLoennsendring = arbeidsavtale.sistLoennsendring,
                    yrke = arbeidsavtale.yrke,
                    gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.gyldighetsperiode)
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}