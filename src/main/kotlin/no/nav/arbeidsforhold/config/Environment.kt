package no.nav.arbeidsforhold.config

data class Environment(
    val corsAllowedOrigins: String = System.getenv("CORS_ALLOWED_ORIGINS") ?: "",
    val corsAllowedSchemes: String = System.getenv("CORS_ALLOWED_SCHEMES") ?: "https",
    val eregApiUrl: String = System.getenv("EREG_API_URL") ?: "http://localhost:8090/ereg/",
    val aaregApiUrl: String = System.getenv("AAREG_API_URL") ?: "http://localhost:8090/aareg/",
    val aaregTargetApp: String = System.getenv("AAREG_TARGET_APP") ?: "",
    val securityJwksIssuer: String = "loginservice",
    val securityJwksUrl: String = System.getenv("LOGINSERVICE_IDPORTEN_DISCOVERY_URL") ?: "https://dummyUrl.com",
    val securityAudience: String = System.getenv("LOGINSERVICE_IDPORTEN_AUDIENCE") ?: "dummyAudience",
)