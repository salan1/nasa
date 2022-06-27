import Dependencies.implementAndroidx
import Dependencies.implementGson
import Dependencies.implementKotlin
import Dependencies.implementMocking
import Dependencies.implementNavigation
import Dependencies.implementTestFramework
import Dependencies.implementTime
import Dependencies.implementUITest

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        testInstrumentationRunner = Config.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementKotlin()
    implementAndroidx()
    implementTestFramework()
    implementUITest()
    implementMocking()
    implementNavigation()
    implementGson()
    implementTime()
    implementation("org.aaronhe:threetenbp-gson-adapter:1.0.2") {
        exclude(module = "threetenbp")
    }
}