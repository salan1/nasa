import Dependencies.implementAndroidArch
import Dependencies.implementAndroidXTest
import Dependencies.implementAndroidx
import Dependencies.implementDI
import Dependencies.implementKotlin
import Dependencies.implementMocking
import Dependencies.implementMvi
import Dependencies.implementNavigation
import Dependencies.implementNetworking
import Dependencies.implementPaging
import Dependencies.implementRoom
import Dependencies.implementTestFramework
import Dependencies.implementTime
import Dependencies.implementUITest
import Dependencies.Glide
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
        getByName("debug") {
            isMinifyEnabled = false
            isRenderscriptDebuggable = true
            manifestPlaceholders["enableCrashReporting"] = "false"
        }
        create("debugRelease") {
            isMinifyEnabled = true
            isRenderscriptDebuggable = true
            manifestPlaceholders["enableCrashReporting"] = "false"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    sourceSets {
        getByName("test").java.srcDir("src/sharedTest/java")
        getByName("androidTest").java.srcDir("src/sharedTest/java")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}

configurations {
    androidTestImplementation {
        exclude(group = "io.mockk", module = "mockk-agent-jvm")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementKotlin()
    implementAndroidx()
    implementAndroidArch()
    implementNavigation()

    implementTestFramework()
    implementUITest()
    implementMocking()
    implementAndroidXTest()

    implementDI()
    implementTime()
    implementation("org.aaronhe:threetenbp-gson-adapter:1.0.2") {
        exclude(module = "threetenbp")
    }
    androidTestImplementation("com.adevinta.android:barista:4.2.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
    implementNetworking()
    implementPaging()
    implementMvi()
    implementRoom()

    implementation(project(":api"))
    implementation(project(":utils"))
    implementation(project(":storage"))
    implementation(project(":mvi"))

    implementation(Glide.Glide) {
        exclude(group = "com.android.support")
    }
    kapt(Glide.Processor)

    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")

}
