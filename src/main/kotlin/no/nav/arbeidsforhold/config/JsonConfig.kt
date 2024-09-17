package no.nav.arbeidsforhold.config

import kotlinx.serialization.json.Json

fun jsonConfig(): Json {
    return Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
        isLenient = true
    }
}
