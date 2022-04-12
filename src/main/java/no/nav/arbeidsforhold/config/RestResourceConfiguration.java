package no.nav.arbeidsforhold.config;

import no.nav.arbeidsforhold.resource.ArbeidsforholdFnrResource;
import no.nav.arbeidsforhold.resource.ArbeidsforholdIdResource;
import no.nav.arbeidsforhold.resource.StatusResource;
import no.nav.security.token.support.jaxrs.JwtTokenContainerRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.processor.OptionsMethodProcessor;
import org.glassfish.jersey.server.wadl.processor.WadlModelProcessor;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.util.Collections;
import java.util.List;

public class RestResourceConfiguration extends ResourceConfig {
    private static final List<Class> WHITELISTED_CLASSES = Collections.singletonList(WadlModelProcessor.OptionsHandler.class);
    private static final List<Class> WHITELISTED_PARENT_CLASSES = Collections.singletonList(OptionsMethodProcessor.class);

    public RestResourceConfiguration() {
        register(JacksonFeature.class);
        register(StatusResource.class);
        register(ArbeidsforholdFnrResource.class);
        register(ArbeidsforholdIdResource.class);
        register(CORSResponseFilter.class);
        register(OidcResourceFilteringFeature.class);
        register(JwtTokenContainerRequestFilter.class);
    }

    public static class OidcResourceFilteringFeature implements DynamicFeature {

        @Override
        public void configure(ResourceInfo resourceInfo, FeatureContext context) {
            if (WHITELISTED_CLASSES.contains(resourceInfo.getResourceClass()) ||
                    WHITELISTED_PARENT_CLASSES.contains(resourceInfo.getResourceClass().getEnclosingClass())) {
                return;
            }
            context.register(OidcResourceFilteringFeature.class);
        }
    }

}
