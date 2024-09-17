# Arbeidsforhold-API

Ktor-backend som skal gi brukeren innsikt i informasjonen NAV har lagret.

Secrets ligger i [Nais Console](https://console.nav.cloud.nais.io/team/personbruker/secrets).

## Lokal kjøring

Kjør main-funksjon i [localServer](src/test/kotlin/no/nav/arbeidsforhold/LocalServer.kt). Endepunkter eksponeres da på localhost:8080, og kan kalles uten token. Merk at alle eksterne avhengigheter er mocket ut.

## Deploy til dev

[Actions](https://github.com/navikt/arbeidsforhold-api/actions) -> Velg workflow -> Run workflow -> Velg branch -> Run workflow

## Prodsetting

-   Lag en PR til master, og merge inn etter godkjenning
-   Lag en release på master med versjon-bump, beskrivende tittel og oppsummering av endringene dine
-   Publiser release for å starte deploy til prod

## Logging

[Kibana](https://logs.adeo.no/app/discover#/view/87c02390-2770-11ed-9b1a-4723a5e7a9db)

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.