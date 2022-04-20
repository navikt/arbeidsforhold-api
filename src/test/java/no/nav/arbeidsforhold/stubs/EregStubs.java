package no.nav.arbeidsforhold.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class EregStubs {

    private EregStubs() {
        // noop
    }

    public static void stubEreg200() {
        stubFor(get(urlPathMatching("/ereg/v1/organisasjon/911742233/noekkelinfo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("ereg-arbeidsgiver.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlPathMatching("/ereg/v1/organisasjon/912783936/noekkelinfo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("ereg-opplysningspliktig.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

    }

    public static void stubEreg500() {
        stubFor(get(urlPathMatching("/ereg/v1/organisasjon/911742233/noekkelinfo"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlPathMatching("/ereg/v1/organisasjon/912783936/noekkelinfo"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
