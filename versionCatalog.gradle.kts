/**
 * Skeleton script for buidling and publishing version catalog.
 */
plugins {
    // plugin for genrating catalog
    `version-catalog`
    // publishing plugin
    `maven-publish`
    // artifact signing plugin
    signing
}

catalog {
    versionCatalog {
        // add dependencies and aliases here
        alias("<alias name>").to("<group-id>", "<artifact-id>").version("<version code>")
    }
}

// sonatype credentials from environment variable using property delegates.
val mavenCentralRepositoryUsername: String by project
val mavenCentralRepositoryPassword: String by project

// Script to publish catalog to maven central.
publishing {
    publications {
        create<MavenPublication>("maven") {
            // artifact meta-data
            groupId = "<group-id>"
            artifactId = "<artifact-id>"
            version = "<version>"
            // component to be published for version catalog
            from(components["versionCatalog"])
            // pom meta data
            pom {
                name.set("<catalog name>")
                description.set("<description>")
                url.set("<website-url>")
                licenses {
                    // licensing info
                    license {
                        // license name
                        name.set("The Apache License, Version 2.0")
                        // license url
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    // developer meta data
                    developer {
                        id.set("<developer-id>")
                        name.set("<developer-name>")
                        email.set("<developer-email>")
                    }
                }
                scm {
                    // version control meta data
                    connection.set("scm:git:git://example.com/my-library.git")
                    developerConnection.set("scm:git:ssh://example.com/my-library.git")
                    url.set("http://example.com/my-library/")
                }
            }
        }
        repositories {
            maven {
                // sonatype account credentials
                credentials {
                    username=mavenCentralRepositoryUsername
                    password=mavenCentralRepositoryPassword
                }
                // publishing url for sonatype
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }
    }
}

signing {
    sign(publishing.publications["maven"])
}