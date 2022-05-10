package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.domain.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto


object ArbeidsavtaleTransformer {

    fun toOutboundArray(inbound: List<Ansettelsesdetaljer>?): List<ArbeidsavtaleDto> {

        val arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            val avtaledto = ArbeidsavtaleDto(
                ansettelsesform = arbeidsavtale.ansettelsesform,
                antallTimerPrUke = arbeidsavtale.antallTimerPrUke,
                arbeidstidsordning = arbeidsavtale.arbeidstidsordning,
                sisteStillingsendring = arbeidsavtale.sisteStillingsprosentendring,
                stillingsprosent = arbeidsavtale.avtaltStillingsprosent,
                sisteLoennsendring = arbeidsavtale.sisteLoennsendring,
                yrke = arbeidsavtale.yrke,
                gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.rapporteringsmaaneder),
                fartsomraade = arbeidsavtale.fartsomraade,
                skipsregister = arbeidsavtale.skipsregister,
                skipstype = arbeidsavtale.fartoeystype
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}