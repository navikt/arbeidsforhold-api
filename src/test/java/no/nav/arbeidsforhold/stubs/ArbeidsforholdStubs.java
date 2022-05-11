package no.nav.arbeidsforhold.stubs;

import org.springframework.http.HttpHeaders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ArbeidsforholdStubs {

    private ArbeidsforholdStubs() {
        // noop
    }

    public static void stubAareg200() {
        stubFor(get(urlPathMatching("/aareg/api/v2/arbeidsforhold/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("arbeidsforhold-single.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlPathMatching("/aareg/api/v2/arbeidstaker/arbeidsforhold"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("arbeidsforhold-list.json")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

    }

    public static void stubAareg500() {
        stubFor(get(urlPathMatching("/aareg/api/v2/arbeidsforhold/.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));

        stubFor(get(urlPathMatching("/aareg/api/v2/arbeidstaker/arbeidsforhold"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("Noe gikk galt")
                        .withHeader(HttpHeaders.CONTENT_TYPE, "application/json")));
    }
}
