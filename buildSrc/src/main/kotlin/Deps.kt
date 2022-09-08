object Ver {
    const val kotlin = "1.7.10" //also update buildSrc/build.gradle.kts
    const val javaee = "8.0"
    const val javax_xml_bind = "2.3.1"

    //test
    const val junit = "5.8.+"
    const val mockk = "1.12.7"
    const val kotest_assertions = "5.4.2"
    const val payara = "5.191"
}

object Deps {
    const val kt_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Ver.kotlin}"
    const val javaee = "javax:javaee-api:${Ver.javaee}"
    const val javax_xml_bind = "javax.xml.bind:jaxb-api:${Ver.javax_xml_bind}"

    const val junit = "org.junit.jupiter:junit-jupiter-api:${Ver.junit}"
    const val junit_engine = "org.junit.jupiter:junit-jupiter-engine:${Ver.junit}"

    const val mockk = "io.mockk:mockk:${Ver.mockk}"
    const val kotest_assertions = "io.kotest:kotest-assertions-core:${Ver.kotest_assertions}"

    const val payara_embedded = "fish.payara.extras:payara-embedded-all:${Ver.payara}"
}
