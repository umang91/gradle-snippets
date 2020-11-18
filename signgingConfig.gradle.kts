/**
* Below snippet is a sample for how to declare a signing config in a build.gradle.kts file.
*/

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "<applicationId>"
        minSdkVersion(17)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
    }
    signingConfigs{
        create("release") {
            keyAlias = "<alias>"
            keyPassword = "<password>"
            storeFile = file("<file_path_of_jks_file>")
            storePassword = "<storePassword>"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}