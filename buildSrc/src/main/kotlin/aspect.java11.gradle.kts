plugins {
    java
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(11))

//even though we're using a java 11 toolchain, we still need to run gradle itself with java 11 or payara will start with the wrong jdk
if (System.getProperty("java.vm.specification.version") != "11") {
    throw IllegalArgumentException("Build config error. This build needs Java 11, but we've got ${System.getProperty("java.vm.specification.version")}.")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.isIncremental = true
    options.isWarnings = false
}
