package no.nav.arbeidsforhold.features.status

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.security.token.support.core.api.Unprotected
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.StreamingOutput


@Component
@Path("/internal")
@ProtectedWithClaims(issuer = "selvbetjening", claimMap = ["acr=Level4"])
class StatusResource {

    @GET
    @Path("isAlive")
    @Unprotected
    fun isAlive(): String {
        return "Ok"
    }

    @GET
    @Path("ping")
    fun ping(): String {
        return "pong"
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("prometheus")
    @Unprotected
    fun prometheus(@QueryParam("name") name: Set<String>?): Response {
        val stream = StreamingOutput { os ->
            val writer = BufferedWriter(OutputStreamWriter(os, StandardCharsets.UTF_8))
            TextFormat.write004(writer, CollectorRegistry.defaultRegistry.filteredMetricFamilySamples(name ?: setOf()))
            writer.flush()
        }
        return Response.ok(stream).build()
    }
}
