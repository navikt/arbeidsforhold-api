package no.nav.arbeidsforhold.service.mapper

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto
import no.nav.arbeidsforhold.testdata.defaultArbeidsforhold
import org.junit.jupiter.api.Test

class ArbeidsforholdMapperTest {

    @Test
    fun `should map basic fields correctly`() {
        val inbound: Arbeidsforhold = defaultArbeidsforhold
        val outbound: ArbeidsforholdDto = inbound.toOutbound(ARBEIDSGIVER_NAVN, OPPLYSNINGSPLIKIG_NAVN)

        assertSoftly(outbound) {
            navArbeidsforholdId shouldBe 1111L
            eksternArbeidsforholdId shouldBe "1111"
            arbeidsgiver?.orgnavn shouldBe ARBEIDSGIVER_NAVN
            opplysningspliktigarbeidsgiver?.orgnavn shouldBe OPPLYSNINGSPLIKIG_NAVN
            ansettelsesperiode.shouldNotBeNull()
            utenlandsopphold.shouldNotBeNull()
            permisjonPermittering.shouldNotBeNull()
            yrke shouldBe "yrke beskrivelse"
            type.shouldBeNull()
            sistBekreftet.shouldBeNull()
            arbeidsavtaler.shouldBeEmpty()
            ansettelsesform.shouldBeNull()
            antallTimerForTimelonnet.shouldBeNull()
            antallTimerPrUke.shouldBeNull()
            arbeidstidsordning.shouldBeNull()
            sisteStillingsendring.shouldBeNull()
            sisteLoennsendring.shouldBeNull()
            stillingsprosent.shouldBeNull()
            fartsomraade.shouldBeNull()
            skipsregister.shouldBeNull()
            skipstype.shouldBeNull()
        }
    }

    @Test
    fun `should map basic + detailed fields correctly`() {
        val inbound: Arbeidsforhold = defaultArbeidsforhold
        val outbound: ArbeidsforholdDto = inbound.toOutboundDetaljert(ARBEIDSGIVER_NAVN, OPPLYSNINGSPLIKIG_NAVN)

        assertSoftly(outbound) {
            navArbeidsforholdId shouldBe 1111L
            eksternArbeidsforholdId shouldBe "1111"
            arbeidsgiver?.orgnavn shouldBe ARBEIDSGIVER_NAVN
            opplysningspliktigarbeidsgiver?.orgnavn shouldBe OPPLYSNINGSPLIKIG_NAVN
            ansettelsesperiode.shouldNotBeNull()
            utenlandsopphold.shouldNotBeEmpty()
            permisjonPermittering.shouldNotBeEmpty().shouldHaveSize(2)
            yrke shouldBe "yrke beskrivelse (Yrkeskode: yrke kode)"
            type shouldBe "type beskrivelse"
            sistBekreftet shouldBe "01.01.2016"
            arbeidsavtaler.shouldBeEmpty()
            ansettelsesform shouldBe "ansettelsesform beskrivelse"
            antallTimerForTimelonnet.shouldNotBeEmpty()
            antallTimerPrUke shouldBe 37.5
            arbeidstidsordning shouldBe "arbeidstidsordning beskrivelse"
            sisteStillingsendring shouldBe "sisteStillingsprosentendring"
            sisteLoennsendring shouldBe "sisteLoennsendring"
            stillingsprosent shouldBe 100.0
            fartsomraade shouldBe "fartsområde beskrivelse"
            skipsregister shouldBe "skipsregister beskrivelse"
            skipstype shouldBe "fartøystype beskrivelse"
        }
    }

    companion object {
        private const val ARBEIDSGIVER_NAVN = "arbeidsgiver navn"
        private const val OPPLYSNINGSPLIKIG_NAVN = "opplysningspliktig navn"
    }
}
