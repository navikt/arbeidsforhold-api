package no.nav.arbeidsforhold.service.mapper


import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import no.nav.arbeidsforhold.consumer.aareg.dto.TimerMedTimeloenn
import no.nav.arbeidsforhold.service.outbound.AntallTimerForTimeloennetDto
import no.nav.arbeidsforhold.service.outbound.PeriodeDto
import no.nav.arbeidsforhold.testdata.TimerMedTimeloennFactory.createTimerMedTimeloenn
import org.junit.jupiter.api.Test

class AntallTimerForTimeloennetMapperTest {

    @Test
    fun `should map all fields correctly`() {
        val inbound: TimerMedTimeloenn = createTimerMedTimeloenn()
        val outbound: AntallTimerForTimeloennetDto = inbound.toOutbound()

        assertSoftly(outbound) {
            antallTimer shouldBe "37.5"
            periode shouldBe PeriodeDto("01.01.2018", "01.01.2019")
            rapporteringsperiode shouldBe "MAI"
        }
    }
}