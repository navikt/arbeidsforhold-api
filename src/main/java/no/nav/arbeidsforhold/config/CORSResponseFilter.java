package no.nav.arbeidsforhold.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static java.util.Arrays.asList;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = asList(
            "http://localhost:8080",
            "https://www-q0.nav.no",
            "https://www-q1.nav.no",
            "https://www.nav.no",
            "https://arbeidsforhold.nais.preprod.local",
            "https://www-q0.nav.no/person/arbeidsforhold",
            "https://personopplysninger-q.nav.no"
    );

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        String origin = request.getHeaderString("Origin");
        if (ALLOWED_ORIGINS.contains(origin)) {
            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}
