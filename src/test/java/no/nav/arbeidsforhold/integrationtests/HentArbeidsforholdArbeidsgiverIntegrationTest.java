package no.nav.arbeidsforhold.integrationtests;

import no.nav.arbeidsforhold.dto.outbound.ArbeidsforholdDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static no.nav.arbeidsforhold.stubs.ArbeidsforholdStubs.stubAareg200;
import static no.nav.arbeidsforhold.stubs.ArbeidsforholdStubs.stubAareg500;
import static no.nav.arbeidsforhold.stubs.EregStubs.stubEreg200;
import static no.nav.arbeidsforhold.stubs.EregStubs.stubEreg500;
import static no.nav.arbeidsforhold.stubs.KodeverkStubs.stubKodeverk200;
import static no.nav.arbeidsforhold.stubs.KodeverkStubs.stubKodeverk500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentArbeidsforholdArbeidsgiverIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubKodeverk200();
        stubAareg200();
        stubEreg200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<ArbeidsforholdDto> response = restTemplate.exchange(
                "/arbeidsforholdinnslag/arbeidsgiver/1337",
                HttpMethod.GET,
                createEntityWithAuthAndFnr(),
                ArbeidsforholdDto.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void kontaktinformasjonSkalGiSkalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforholdinnslag/arbeidsgiver/1337",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilMotAareg() {
        stubAareg500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforholdinnslag/arbeidsgiver/1337",
                HttpMethod.GET,
                createEntityWithAuthAndFnr(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi200MedFeilMotEreg() {
        stubEreg500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforholdinnslag/arbeidsgiver/1337",
                HttpMethod.GET,
                createEntityWithAuthAndFnr(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGi500MedFeilMotKodeverk() {
        stubKodeverk500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforholdinnslag/arbeidsgiver/1337",
                HttpMethod.GET,
                createEntityWithAuthAndFnr(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    private HttpEntity<HttpHeaders> createEntityWithAuthAndFnr() {
        HttpHeaders headers = createAuthHeader(IDENT);
        headers.add("Fnr-Arbeidstaker", IDENT);
        return new HttpEntity<>(headers);
    }
}