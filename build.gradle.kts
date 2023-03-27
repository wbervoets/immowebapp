// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress( "DSL_SCOPE_VIOLATION")
plugins {
    // red underline should be fixed in Gradle 8.1 https://youtrack.jetbrains.com/issue/KTIJ-19369 / https://github.com/gradle/gradle/issues/23784
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}