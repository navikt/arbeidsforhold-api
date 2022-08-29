package no.nav.arbeidsforhold.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS"),
    val corsAllowedSchemes: String = System.getenv("CORS_ALLOWED_SCHEMES"),

    val eregApiUrl: String = System.getenv("EREG_API_URL"),
    val aaregApiUrl: String = System.getenv("AAREG_API_URL"),

    val aaregTargetApp: String = System.getenv("AAREG_TARGET_APP"),
)