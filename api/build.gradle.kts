import Dependencies.implementKotlin
import Dependencies.implementNetworking
import Dependencies.implementTime
import Dependencies.implementUnitTestFramework

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


dependencies {
    implementKotlin()
    implementUnitTestFramework()
    implementNetworking()
    implementTime()
    implementation("org.aaronhe:threetenbp-gson-adapter:1.0.2") {
        exclude(module = "threetenbp")
    }
}