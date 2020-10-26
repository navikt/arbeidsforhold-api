package no.nav.arbeidsforhold.config;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static java.util.Arrays.asList;

@Provider
public class CORSResponseFilter implements ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = asList(
            "https://arbeidsforhold.nais.preprod.local",
            "http://localhost:3000",
            "https://www-q0.nav.no",
            "https://www-q1.nav.no",
            "https://www-q2.nav.no",
            "https://www-q6.nav.no",
            "https://www.nav.no",
            "https://personopplysninger-q.nav.no",
            "https://arbeidsgiver-q.nav.no",
            "https://arbeidsgiver.nav.no"
    );

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        String origin = request.getHeaderString("Origin");
        if (ALLOWED_ORIGINS.contains(origin)) {
            // Preflight
            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            response.getHeaders().add("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization, fnr-arbeidstaker");
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");
            response.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
    }
}
