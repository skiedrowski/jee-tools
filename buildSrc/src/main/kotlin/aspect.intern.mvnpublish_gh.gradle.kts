// Required properties:
// GH_USERNAME
// GH_TOKEN
//
// may be set via gradle.properties (systemProp.GH_USERNAME=peter) or via commandline (-DGH_USERNAME=peter)
//
// Required in build.gradle:
// plugins {
//    id("maven-publish")
// }
//
// The script also depends on valid
//      version = <maven version>
//      group = <maven group id>
//
plugins {
    `maven-publish`
    id("aspect.intern.sourcejar")
    id("aspect.intern.testjar")
}

// if there is a failure like
//
// * What went wrong:
// Execution failed for task ':script-api:publishMavenPublicationToMavenRepository'.
// > Failed to publish publication 'maven' to repository 'maven'
// > Authentication scheme 'all'(Authentication) is not supported by protocol 'file'
//
// you probably did not set the System properties MVN_*, i.e. MVN_PUBLISH_URL_PREFIX
//
//
// if publish tries to use releases for a snapshot, project.version is set too late (after configuration of this script)

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
//            groupId = project.group
//            artifactId = "library"
//            version = project.version

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["jarTestClasses"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${System.getProperty("GH_USERNAME")}/${project.rootProject.name}")
            credentials {
                username = System.getProperty("GH_USERNAME")
                password = System.getProperty("GH_TOKEN")
            }
        }
    }
}
