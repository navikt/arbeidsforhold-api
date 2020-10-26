[![CircleCI](https://circleci.com/gh/navikt/arbeidsforhold-api.svg?style=svg&circle-token=6dda2eb4424996fe6f12a5fdcfd831abca3c149b)](https://circleci.com/gh/navikt/arbeidsforhold-api)

# Arbeidsforhold-API

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
