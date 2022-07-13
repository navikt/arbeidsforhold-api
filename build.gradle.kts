import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm").version(Kotlin.version)
    kotlin("plugin.allopen").version(Kotlin.version)
    kotlin("plugin.serialization").version(Kotlin.version)

    id(Shadow.pluginId) version (Shadow.version)
    id(Versions.pluginId) version Versions.version // ./gradlew dependencyUpdates to check for new versions
    application
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(NAV.Security.tokenValidatorCore)
    implementation(NAV.Security.tokenValidatorJaxrs)
    implementation(NAV.Common.rest)
    implementation(NAV.Common.log)
    implementation(Arrow.core)
    implementation(SpringBoot.starterJetty)
    implementation(SpringBoot.starterJersey)
    implementation(SpringBoot.starterWeb)
    implementation(Kotlin.jdk8)
    implementation(Kotlin.junit5)
    implementation(Slf4j.api)
    implementation(CommonsLogging.logging)
    implementation(Logstash.logbackEncoder)
    implementation(Microutils.kotlinLogging)
    implementation(Jackson.kotlinModule)
    implementation(Natpryce.konfig)
    implementation(Micrometer.registryPrometheus)
    implementation(Jakarta.xmlBindApi)
    implementation(Jaxb.runtime)
    testImplementation(SpringBoot.starterTest)
    testImplementation(Junit.api)
    testImplementation(Junit.engine)
    testImplementation(NAV.Security.tokenValidatorSpringTest)
    testImplementation(Tomakehurst.wiremock)
    testImplementation(SpringCloud.stubRunner)
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events("passed", "skipped", "failed")
        }
    }

    register("runServer", JavaExec::class) {

        environment("CORS_ALLOWED_ORIGINS", "localhost:9002")

        environment("NAIS_CLUSTER_NAME", "dev-gcp")
        environment("NAIS_NAMESPACE", "personbruker")

        main = application.mainClassName
        classpath = sourceSets["main"].runtimeClasspath
    }
}

apply(plugin = Shadow.pluginId)