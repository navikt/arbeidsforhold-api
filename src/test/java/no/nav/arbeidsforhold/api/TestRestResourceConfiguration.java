package no.nav.arbeidsforhold.api;

import no.nav.arbeidsforhold.config.RestResourceConfiguration;
import no.nav.security.oidc.test.support.jersey.TestTokenGeneratorResource;

public class TestRestResourceConfiguration extends RestResourceConfiguration {

    public TestRestResourceConfiguration() {

        register(TestTokenGeneratorResource.class);

    }

}
