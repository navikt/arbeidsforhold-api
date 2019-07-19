[![CircleCI](https://circleci.com/gh/navikt/arbeidsforhold-api.svg?style=svg&circle-token=6dda2eb4424996fe6f12a5fdcfd831abca3c149b)](https://circleci.com/gh/navikt/arbeidsforhold-api)

# Arbeidsforhold-API

Spring Boot backend som skal gi brukeren innsikt i informasjonen NAV har lagret. <br>
Applikasjonen har ingen avhengigheter til nexus og kan kjøres lokalt på laptop.

## Deployering

Applikasjonen bygges automatisk til dev / https://www-q0.nav.no/person/arbeidsforhold-api ved merge til master eller ved manuell godkjenning i [CircleCI](https://circleci.com/gh/navikt/workflows/arbeidsforhold-api). <br><br>
For å lansere applikasjonen til produksjon / https://www.nav.no/person/arbeidsforhold-api, knytt en commit til en [Git tag](https://git-scm.com/book/en/v2/Git-Basics-Tagging):

```
git tag -a vX.X.X -m "Din melding"
```

Push den nye versjonen til GitHub og merge til master.

```
git push --tags
```

Godkjenn produksjonssettingen i [CircleCI](https://circleci.com/gh/navikt/workflows/arbeidsforhold-api).

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