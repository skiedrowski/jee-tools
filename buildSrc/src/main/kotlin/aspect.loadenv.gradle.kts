val environment = if (project.hasProperty("env")) project.property("env") else "dev"
project.extra["environment"] = environment
println("Environment is set to $environment")

apply(from = "${rootProject.projectDir}/gradle/env_$environment.gradle.kts")

// create an env file for each environment in <rootproject>/gradle
//
//project.extra["config"] = mapOf(
//    Pair("ignoreTestFailures", true),
//    Pair("nexus_home", "https://my_mvn_repo")
//)
//
// name the file env_<environment>.gradle.kts

// accessing properties:
//tasks.withType<Test>().configureEach {
//    //the more elegant `val config by project.rootProject.extra` does not compile
//    val config = project.rootProject.extra["config"] as Map<String, *>
//    ignoreFailures = config["ignoreTestFailures"] as Boolean
//}
