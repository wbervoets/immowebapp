// red underline should be fixed in Gradle 8.1 https://youtrack.jetbrains.com/issue/KTIJ-19369 / https://github.com/gradle/gradle/issues/23784
@file:Suppress( "DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.android.junit5) // add support for JUnit5 based tests
}

android {
    namespace = "be.wimbervoets.immowebservice"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        apiVersion = "1.8" // Allow using declarations only from the specified version of bundled libraries
        languageVersion = "1.8" // Provide source compatibility with the specified version of Kotlin
    }
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinxserializationconverter)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)


    // Writing and executing Unit Tests on the JUnit5 Platform
    testImplementation(project(":junit5extensions")) // transitive dependency on libs.junit.jupiter.api / libs.junit.jupiter.params / mockwebserver
    testRuntimeOnly(libs.junit.jupiter.engine)
    
    // TestScope for Kotlin coroutines
    testImplementation(libs.kotlinx.coroutines.test)

    // Mocking library
    testImplementation(libs.mockk.android)
}