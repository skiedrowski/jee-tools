plugins {
    `java-test-fixtures`
    kotlin("jvm")

    id("org.jetbrains.kotlin.plugin.noarg")
    id("org.jetbrains.kotlin.plugin.allopen")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "11"
        // see @JvmDefaultWithoutCompatibility
        // and https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
        // and https://youtrack.jetbrains.com/issue/KT-4779
        freeCompilerArgs = freeCompilerArgs.plus("-Xjvm-default=all-compatibility")
    }
}

configure<org.jetbrains.kotlin.allopen.gradle.AllOpenExtension> {
    annotation("com.github.skiedrowski.tools.kt.compiler.allopen.AllOpen")
    annotation("javax.ejb.Singleton")
    annotation("javax.ejb.Stateless")
    annotation("javax.persistence.Entity")
    annotation("javax.ws.rs.ApplicationPath")
    annotation("javax.ws.rs.Path")
}


configure<org.jetbrains.kotlin.noarg.gradle.NoArgExtension> {
    annotation("com.github.skiedrowski.tools.kt.compiler.noarg.NoArg")
    annotation("javax.ejb.Singleton")
    annotation("javax.ejb.Stateless")
    annotation("javax.persistence.Entity")
    annotation("javax.ws.rs.Path")
    annotation("javax.xml.bind.annotation.XmlRootElement")
    annotation("javax.xml.bind.annotation.XmlAccessorType")
}

dependencies {
    implementation(Deps.kt_stdlib_jdk8)

    // kotlin-reflect is (just) used by mockito-kotlin. However the version should match kotlin version, so we need
    // to explicitly to avoid conflicting versions on the (test) classpath
//    testFixturesApi(Deps.kt_reflect)
//    testFixturesApi(Deps.mockito_kotlin)
//    testFixturesApi(Deps.hamkrest)

    testFixturesApi(Deps.mockk)
    testFixturesApi(Deps.kotest_assertions)
}
