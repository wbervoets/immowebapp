// red underline should be fixed in Gradle 8.1 https://youtrack.jetbrains.com/issue/KTIJ-19369 / https://github.com/gradle/gradle/issues/23784
@file:Suppress( "DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    api(libs.junit.jupiter.api)
    api(libs.junit.jupiter.params) // Parametrized tests
    api(libs.kotlinx.serialization.json)
    api(libs.okhttp.mockserver)

}