package no.nav.arbeidsforhold.service.transformer

import no.nav.arbeidsforhold.consumer.aareg.dto.Ansettelsesdetaljer
import no.nav.arbeidsforhold.service.outbound.ArbeidsavtaleDto


object ArbeidsavtaleTransformer {

    fun toOutboundArray(
        inbound: List<Ansettelsesdetaljer>,
        inkluderYrkeskode: Boolean,
    ): List<ArbeidsavtaleDto> {
        return inbound.map {
            ArbeidsavtaleDto(
                ansettelsesform = it.ansettelsesform?.beskrivelse,
                antallTimerPrUke = it.antallTimerPrUke,
                arbeidstidsordning = it.arbeidstidsordning?.beskrivelse,
                sisteStillingsendring = it.sisteStillingsprosentendring,
                stillingsprosent = it.avtaltStillingsprosent,
                sisteLoennsendring = it.sisteLoennsendring,
                yrke = buildString {
                    append(it.yrke?.beskrivelse)
                    if (inkluderYrkeskode) append(" (Yrkeskode: ${it.yrke?.kode})")
                },
                gyldighetsperiode = PeriodeTransformer.toOutboundfromGyldighetsperiode(it.rapporteringsmaaneder),
                fartsomraade = it.fartsomraade?.beskrivelse,
                skipsregister = it.skipsregister?.beskrivelse,
                skipstype = it.fartoeystype?.beskrivelse
            )
        }
    }

}