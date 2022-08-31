/**
*
* To publish an android library add the below DSLs in the build.gradle.kts file of the library module.
* This script is tested for Android Gradle plugin 7.0.0 and above.
* 
* Commands to publish
* 
* Upload the library to the remote repository
* ./gradlew <module-name>:publish --no-daemon --no-parallel
*
* Upload the library to the local maven repository of the system path "~/.m2/repository"
* ./gradlew <module-name>:publishToMavenLocal
*
*/
plugins {
    ...
    `maven-publish`
    signing
}

android {
    ...
    // variants to be published.
    publishing {
        // variants to be published.
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = <group-id>
            artifactId = <artifact-name>
            version = <version-name>
            afterEvaluate {
                from(components["release"])
            }
            pom {
                // meta data for the library
                name.set(<library name>)
                description.set(<library description>)
                url.set(<url of the project>)
                licenses {
                    license {
                        name.set(<license name>)
                        url.set(<license url>)
                    }
                }
                developers {
                    // developer info
                    developer {
                        id.set(<developer-id>)
                        name.set(<developer-name>)
                    }
                }
                scm {
                    connection.set(<scm connection url>)
                    developerConnection.set(<scm dev connection url>)
                    url.set(<url of the project>)
                }
            }
        }
        repositories {
            maven {
                // credentials to upload to maven central
                credentials {
                    username = <username>
                    password = <password>
                }
                // location to upload the library
                url = uri(<url to upload to>)
            }
        }
    }
}

signing {
    sign(publishing.publications["release"])
}