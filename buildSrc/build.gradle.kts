repositories {
    gradlePluginPortal() // so that external plugins can be resolved in dependencies section
}

plugins {
//    `groovy-gradle-plugin` //for src/main/groovy
    `kotlin-dsl` //for src/main/kotlin
}

dependencies {
    //cannot use Deps here
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10") //version must match Deps.kotlin
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.7.10") //version must match Deps.kotlin
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.7.10") //version must match Deps.kotlin
}
