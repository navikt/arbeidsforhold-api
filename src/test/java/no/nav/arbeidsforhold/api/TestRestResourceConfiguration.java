package no.nav.arbeidsforhold.api;

import no.nav.arbeidsforhold.config.RestResourceConfiguration;
import no.nav.security.token.support.spring.test.EnableMockOAuth2Server;

@EnableMockOAuth2Server
public class TestRestResourceConfiguration extends RestResourceConfiguration {

    public TestRestResourceConfiguration() {

    }

}
