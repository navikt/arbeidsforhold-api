package no.nav.arbeidsforhold.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class KodeverkStubs {

    private KodeverkStubs() {
        // noop
    }

    public static void stubKodeverk200() {
        stubKodeverkYrker200();
        stubKodeverkArbeidsforholdstyper200();
        stubKodeverkArbeidstidsordninger200();
        stubKodeverkFartsomraader200();
        stubKodeverkSkipsregistre200();
        stubKodeverkSkipstyper200();
        stubKodeverkLand200();
        stubKodeverkPermisjonstyper200();
        stubKodeverkSluttaarsaker200();
    }

    public static void stubKodeverk500() {
        stubKodeverkYrker500();
        stubKodeverkArbeidsforholdstyper500();
        stubKodeverkArbeidstidsordninger500();
        stubKodeverkFartsomraader500();
        stubKodeverkSkipsregistre500();
        stubKodeverkSkipstyper500();
        stubKodeverkLand500();
        stubKodeverkPermisjonstyper500();
        stubKodeverkSluttaarsaker500();
    }

    private static void stubKodeverkYrker200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Yrker/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-yrker.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkArbeidsforholdstyper200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Arbeidsforholdstyper/.*"))
                .atPriority(2)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-arbeidsforholdstyper.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkArbeidstidsordninger200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Arbeidstidsordninger/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-arbeidstidsordninger.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkFartsomraader200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Fartsomr%C3%A5der/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-fartsomraader.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSkipsregistre200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Skipsregistre/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-skipsregistre.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSkipstyper200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Skipstyper/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-skipstyper.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkLand200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/LandkoderISO2/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-land.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkPermisjonstyper200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/PermisjonsOgPermitteringsBeskrivelse/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-permisjonstyper.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSluttaarsaker200() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Slutt%C3%A5rsakAareg/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("kodeverk-sluttaarsaker.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkYrker500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Yrker/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkArbeidsforholdstyper500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Arbeidsforholdstyper/.*"))
                .atPriority(2)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkArbeidstidsordninger500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Arbeidstidsordninger/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkFartsomraader500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Fartsomr%C3%A5der/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSkipsregistre500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Skipsregistre/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSkipstyper500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Skipstyper/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkLand500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/LandkoderISO2/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkPermisjonstyper500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/PermisjonsOgPermitteringsBeskrivelse/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

    private static void stubKodeverkSluttaarsaker500() {
        stubFor(get(urlPathMatching("/kodeverk/api/v1/kodeverk/Slutt%C3%A5rsakAareg/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }

}
