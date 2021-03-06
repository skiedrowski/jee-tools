Gradle Build
============
The project uses gradle buildscripts maintained in [jvm-project-template](https://github.com/skiedrowski/jvm-project-template). The scripts are conveniently organized in the `buildscripts` folder. This folder contains a the file [buildscripts.info.md](../buildscripts/buildscripts.info_.md) which includes the VERSION of the jvm-project-template used. 

Each script implements a build feature in a reasonable granularity. They should be included into the main/module build.gradle to keep the build.gradle itself small and readable.

In addition to the *.gradle build scripts, this folder also contains [snippets](../buildscripts/snippets/_info_.md) with gradle snippets that one might want to adapt and copy into a `build.gradle`.

The main build.gradle contains a number of `TODO` tags which indicate where customization is very likely needed.

Common Gradle Tasks
-------------------
If the build is configured accordingly there are the following common build tasks available:

clean build directory

    gradlew clean

run tests (only those which are NOT annotated with @Category(SequentialTest.class), if `execSequentialTestsSeparately.gradle` is used)

	gradlew test

run a single test 

	gradlew test --tests=[testMethod, testClass, package, suite, ...]
	
see [Test filtering](https://docs.gradle.org/current/userguide/java_plugin.html#test_filtering)

run a single test - old mechanism (faster for large projects): 

	gradlew -Dtest.single=<SimpleTestClassName> test
	
The name of the test is just the simple class name.

run slow tests (only those which are annotated with @Category(SequentialTest.class), if `execSequentialTestsSeparately.gradle` is used)

	gradlew sequentialTest
	
run integration tests (if configured in task "integrationTest") 

	gradlew integrationTest
	
run single integration test

	./gradlew -DintegrationTest.single=OrderITest integrationTest
	
The name of the test is just the simple class name.


CodeCoverage (JaCoCo)

	gradlew jacocoTestReport

build jar, run tests, run slow tests, run checks

	gradlew build

refresh (especially SNAPSHOT) dependencies, i.e.

	gradlew clean build --refresh-dependencies

show dependency tree

	gradlew dependencies
	gradlew :module:dependencies

### Maven (Upstream)
Maven tasks depend on [mavenPublish.gradle](../buildscripts/mavenPublish.md) and the according configuration variables.
Depending on the build, the following artifacts are uploaded: classes, sources, javadoc; snapshots/releases depending on `version`

install into local maven

	gradlew publishToMavenLocal

upload into nexus (or another repositoy)

	gradlew publish
	
###Application Tasks
Application Tasks depend on somethink like [applicationSnippets](../buildscripts/snippets/applicationSnippets.md).

create app in build/install

	gradlew installDist

create zip containing the whole app (zips the result of installApp, suitable for distribution)

	gradlew distZip

run the application

    gradlew run

###Possible Release Process

* Update CHANGELOG
* set release version (without -SNAPSHOT) in local build.gradle
* commit && push
* if the version to be released already exists in the repository, it has to be deleted firstly (or you'll get "error code 400: bad request")
* run gradlew clean build [integrationTest] upload
* set next snapshot version (with -SNAPSHOT) in local build.gradle
* commit && push
