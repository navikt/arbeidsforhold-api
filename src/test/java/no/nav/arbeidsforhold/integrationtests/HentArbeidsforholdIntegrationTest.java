package no.nav.arbeidsforhold.integrationtests;

import no.nav.arbeidsforhold.service.outbound.ArbeidsforholdDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static no.nav.arbeidsforhold.stubs.ArbeidsforholdStubs.stubAareg200;
import static no.nav.arbeidsforhold.stubs.ArbeidsforholdStubs.stubAareg500;
import static no.nav.arbeidsforhold.stubs.EregStubs.stubEreg200;
import static no.nav.arbeidsforhold.stubs.EregStubs.stubEreg500;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class HentArbeidsforholdIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setup() {
        stubAareg200();
        stubEreg200();
    }

    @Test
    void skalGi200MedGyldigToken() {
        ResponseEntity<List<ArbeidsforholdDto>> response = restTemplate.exchange(
                "/arbeidsforhold",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    @Test
    void skalGiSkalGi401UtenGyldigToken() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforhold",
                HttpMethod.GET,
                createEntityWithoutHeaders(),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.UNAUTHORIZED)));
    }

    @Test
    void skalGi500MedFeilMotAareg() {
        stubAareg500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforhold",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    void skalGi200MedFeilMotEreg() {
        stubEreg500();

        ResponseEntity<String> response = restTemplate.exchange(
                "/arbeidsforhold",
                HttpMethod.GET,
                createEntityWithAuthHeader(IDENT),
                String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }
}