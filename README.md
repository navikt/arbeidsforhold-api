# Arbeidsforhold-API

![Deploy-to-prod](https://github.com/navikt/arbeidsforhold-api/workflows/Deploy-to-prod/badge.svg) <br>
![Deploy-to-q0](https://github.com/navikt/arbeidsforhold-api/workflows/Deploy-to-q0/badge.svg)
![Deploy-to-q1](https://github.com/navikt/arbeidsforhold-api/workflows/Deploy-to-q1/badge.svg)
![Deploy-to-q2](https://github.com/navikt/arbeidsforhold-api/workflows/Deploy-to-q2/badge.svg)
![Deploy-to-q6](https://github.com/navikt/arbeidsforhold-api/workflows/Deploy-to-q6/badge.svg)

Spring Boot backend som skal gi brukeren innsikt i informasjonen NAV har lagret. <br>
Applikasjonen har ingen avhengigheter til nexus og kan kjøres lokalt på laptop.

## Deployering

Q6:
```
git tag -a vX.X.X-dev
```

Q1, Q2, Q6:
```
git tag -a vX.X.X-test
```
Q0, prod:
```
git tag -a vX.X.X-prod
```

Push den nye versjonen til GitHub og merge til master.
```
git push && git push --tags
```

## Lokalt Kjøring

For å kjøre opp løsningen lokalt <br>
Kjør [TestLauncher](src/test/java/no/nav/arbeidsforhold/api/TestLauncher.java).

## Logging

Feil ved API-kall blir logget via frontendlogger og vises i Kibana<br>
[https://logs.adeo.no](https://logs.adeo.no/app/kibana#/discover/ad01c200-4af4-11e9-a5a6-7fddb220bd0c)

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.
