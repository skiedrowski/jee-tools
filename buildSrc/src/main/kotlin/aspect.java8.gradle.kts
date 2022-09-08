plugins {
    java
}
//jasper currently depends on JDK 8
java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

//even though we're using a java 8 toolchain, we still need to run gradle itself with java 8 or payara will start with the wrong jdk
if (System.getProperty("java.vm.specification.version") != "1.8") {
    throw IllegalArgumentException("Build config error. This build needs Java 1.8, but we've got ${System.getProperty("java.vm.specification.version")}.")
}
