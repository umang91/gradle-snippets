/**
* Below snippet is a sample for how to declare a private repository in a build.gradle.kts file.
*/

repositories {
    maven(url="<maven_url>"){
        credentials {
            username="<username>"
            password="<API_KEY>"
        }
    }
}