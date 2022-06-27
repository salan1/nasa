// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(GradleConfig.Android)
        classpath(GradleConfig.Kotlin)
        classpath(GradleConfig.Navigation)
        classpath(GradleConfig.Hilt)
    }
}

allprojects {
    repositories {
        google()
        maven(url = "https://jitpack.io")
        jcenter()
        maven(url = "https://maven.google.com/")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}