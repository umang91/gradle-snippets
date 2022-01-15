/**
 * Gradle file for Android Application project in Kotlin with commonly used configurations.
 */
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "dev.assemblage.gradlekotlin"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // defining signing configuration
    signingConfigs {
        // use getByName() for "debug" since signing config debug exists by default and we are
        // overriding it here.
        // Use create() if want to create a new named config like "release"

        /**
         * This configuration could be directly assigned to the variables or read from a file shown below. Following would the contents of the keystore.properties file.
         * storeFilePath=storefile.jks
         * storeFilePath=storefile.jks
         * storePassword=<store_password>
         * keyAlias=<key_alias>
         * keyPassword=<password>
         */
        val properties = Properties()
        // replace with acutal path, assumption here is that the file is inside the default app module.
        properties.load(FileInputStream("${project.rootDir.absolutePath}/app/keystore.properties"))
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = file(properties.getProperty("storeFilePath"))
            storePassword = properties.getProperty("storePassword")
        }
        getByName("debug") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = file(properties.getProperty("storeFilePath"))
            storePassword = properties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("release") {
            // signing key for the build type.
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    
    // Refer to the official documentation(https://developer.android.com/studio/build/build-variants#build-types) to learn more about build types and product flavors.

    flavorDimensions.add("sampleDimen")

    // creating product flavors
    productFlavors {
        create("flavour1") {
            dimension = "sampleDimen"
            applicationIdSuffix = ".flavour.one"
            signingConfig = signingConfigs.getByName("release")
        }
        create("flavour2") {
            dimension = "sampleDimen"
            applicationIdSuffix = ".flavour.two"
            signingConfig = signingConfigs.getByName("debug")
        }
        create("flavour3") {
            dimension = "sampleDimen"
            applicationIdSuffix = ".flavour.three"
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    // build type specific to a specific buildType.
    // <flaourName><Dependency type>("<artifact co-ordinates>")
    "flavour1Implementation"("dev.assemblage:fcm-client:4.0.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}