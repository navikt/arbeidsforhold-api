package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto


object ArbeidsavtaleTransformer {

    fun toOutboundArray(
        inbound: List<Ansettelsesdetaljer>?,
        inkluderYrkeskode: Boolean = false
    ): List<ArbeidsavtaleDto> {

        val arbeidsavtaleDtoArray = ArrayList<ArbeidsavtaleDto>()

        for (arbeidsavtale in inbound.orEmpty()) {
            val avtaledto = ArbeidsavtaleDto(
                ansettelsesform = arbeidsavtale.ansettelsesform?.beskrivelse,
                antallTimerPrUke = arbeidsavtale.antallTimerPrUke,
                arbeidstidsordning = arbeidsavtale.arbeidstidsordning?.beskrivelse,
                sisteStillingsendring = arbeidsavtale.sisteStillingsprosentendring,
                stillingsprosent = arbeidsavtale.avtaltStillingsprosent,
                sisteLoennsendring = arbeidsavtale.sisteLoennsendring,
                yrke = if (inkluderYrkeskode) "${arbeidsavtale.yrke?.beskrivelse} (Yrkeskode: ${arbeidsavtale.yrke?.kode})" else arbeidsavtale.yrke?.beskrivelse,
                gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(arbeidsavtale.rapporteringsmaaneder),
                fartsomraade = arbeidsavtale.fartsomraade?.beskrivelse,
                skipsregister = arbeidsavtale.skipsregister?.beskrivelse,
                skipstype = arbeidsavtale.fartoeystype?.beskrivelse
            )
            arbeidsavtaleDtoArray.add(avtaledto)
        }

        return arbeidsavtaleDtoArray
    }

}