plugins {
    id("war")
}

tasks.war {
    archiveFileName.set("${project.name}.war")
}

//should by added by war plugin?!
//configurations {
//    val runtime by configurations.creating
//}

//tasks.withType<War>().configureEach {
//    archiveFileName.set("${project.name}.war")
//}
