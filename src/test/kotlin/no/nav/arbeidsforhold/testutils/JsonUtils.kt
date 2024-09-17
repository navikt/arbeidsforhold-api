package no.nav.arbeidsforhold.testutils


fun readJsonFile(name: String): String {
    return {}.javaClass.getResource(name)!!.readText()
}