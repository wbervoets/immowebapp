@file:Suppress( "DSL_SCOPE_VIOLATION")
// red underline should be fixed in Gradle 8.1 https://youtrack.jetbrains.com/issue/KTIJ-19369 / https://github.com/gradle/gradle/issues/23784
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.android.junit5)
}

android {
    namespace = "be.wimbervoets.immowebapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "be.wimbervoets.immowebapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.android.compose.compiler.get()
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":immowebservice"))

    implementation(libs.androidx.activity.ktx)
    implementation(libs.timber)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.androidx.constraintlayout)
    implementation(libs.material.components)

    // Compose
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata) // Livedata observeAsState
    implementation(libs.androidx.compose.ui) // Text, ...
    implementation(libs.androidx.compose.material3) // Material3Theme
    implementation(libs.androidx.compose.ui.tooling) // @Preview compose
    implementation(libs.coil.compose)
    implementation(libs.androidx.compose.constraintlayout)
    implementation(libs.androidx.compose.activity) // setContent { .. } on ComponentActivity

    // Writing and executing Unit Tests on the JUnit5 Platform
    testImplementation(project(":junit5extensions"))
    testRuntimeOnly(libs.junit.jupiter.engine)
    
    // TestScope for Kotlin coroutines
    testImplementation(libs.kotlinx.coroutines.test)

    // Mocking library
    testImplementation(libs.mockk.android)

    // LiveData testing
    testImplementation(libs.livedata.testing.ktx)
}