package no.nav.arbeidsforhold.testdata

import no.nav.arbeidsforhold.consumer.aareg.dto.Arbeidsforhold
import no.nav.arbeidsforhold.testdata.AnsettelsesdetaljerFactory.createAnsettelsesdetaljer
import no.nav.arbeidsforhold.testdata.AnsettelsesperiodeFactory.createAnsettelsesperiode
import no.nav.arbeidsforhold.testdata.IdenterFactory.createIdenter
import no.nav.arbeidsforhold.testdata.KodeverksentitetFactory.createKodeverksentitet
import no.nav.arbeidsforhold.testdata.PermisjonPermitteringFactory.createPermisjonPermittering
import no.nav.arbeidsforhold.testdata.TimerMedTimeloennFactory.createTimerMedTimeloenn
import no.nav.arbeidsforhold.testdata.UtenlandsoppholdFactory.createUtenlandsopphold


object ArbeidsforholdFactory {

    fun createArbeidsforhold() = Arbeidsforhold(
        ansettelsesperiode = createAnsettelsesperiode(),
        timerMedTimeloenn = listOf(createTimerMedTimeloenn()),
        ansettelsesdetaljer = listOf(createAnsettelsesdetaljer()),
        navArbeidsforholdId = 1111,
        id = "1111",
        arbeidssted = createIdenter(),
        permisjoner = listOf(createPermisjonPermittering()),
        permitteringer = listOf(createPermisjonPermittering()),
        sistBekreftet = "01.01.2016",
        type = createKodeverksentitet(),
        utenlandsopphold = listOf(createUtenlandsopphold())
    )
}