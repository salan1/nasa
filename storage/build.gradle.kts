import Dependencies.implementKotlin
import Dependencies.implementAndroidArch
import Dependencies.implementMocking
import Dependencies.implementMvi
import Dependencies.implementRoom
import Dependencies.implementTestFramework
import Dependencies.implementTime

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
}

dependencies {
    implementKotlin()
    implementAndroidArch()
    implementTestFramework()
    implementMocking()
    implementRoom()

    implementTime()
    implementation("org.aaronhe:threetenbp-gson-adapter:1.0.2") {
        exclude(module = "threetenbp")
    }
}
