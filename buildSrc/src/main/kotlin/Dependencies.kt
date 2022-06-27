import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    fun DependencyHandler.implementKotlin() {
        implementAll(
            Kotlin.Reflect,
            Kotlin.CoroutineCore,
            Kotlin.CoroutineCore,
            Kotlin.CoroutineAndroid,
            Kotlin.CoroutineRx,
        )

        testImplementAll(
            Kotlin.CoroutineTest
        )
    }

    fun DependencyHandler.implementAndroidx() {
        implementAll(
            AndroidX.AppCompat,
            AndroidX.Runtime,
            AndroidX.Annotation,
            AndroidX.AndroidXLegacy,
            AndroidX.RecyclerView,
            AndroidX.Preference,
            AndroidX.PreferenceLegacy,
            AndroidX.Splash,
            AndroidX.ConstraintLayout,
            AndroidX.Material,
            AndroidX.Fragment,
            Kotlin.AndroidX,
        )
    }

    fun DependencyHandler.implementAndroidArch() {
        implementAll(
            Lifecycle.Runtime,
            Lifecycle.Reactivestreams,
            Lifecycle.Livedata,
            Lifecycle.Viewmodel
        )
        kaptImplementAll(Lifecycle.Java8)
    }

    fun DependencyHandler.implementNavigation() {
        implementAll(
            Navigation.Fragment,
            Navigation.UI,
            Navigation.Dynamic,
            Navigation.Compose
        )
        androidTestImplementAll(Navigation.Testing)
    }

    fun DependencyHandler.implementUnitTestFramework() {
        testImplementAll(
            Testing.Junit,
            Testing.Truth,
            Testing.Turbine
        )
    }

    fun DependencyHandler.implementTestFramework() {
        testImplementAll(
            Testing.Junit,
            Robolectric.Core,
            Testing.Truth,
            Testing.Turbine,
        )

        androidTestImplementAll(
            Testing.Junit,
            Testing.Truth,
            Espresso.Core,
            Espresso.Contrib,
            Espresso.Intents,
        )
    }

    fun DependencyHandler.implementMocking() {
        testImplementAll(
            OkHttp.Mock,
            Testing.Mockk,
            Testing.MockkJvm
        )
    }

    fun DependencyHandler.implementDI() {
        implementAll(
            Hilt.Hilt,
            Hilt.Fragment,
            Hilt.Work,
        )
        testImplementAll(Hilt.Test)
        androidTestImplementAll(Hilt.Test)
        kaptImplementAll(
            Hilt.Compiler,
            Hilt.AndroidXCompiler
        )
        add("testAnnotationProcessor", Hilt.Compiler)
        add("androidTestAnnotationProcessor", Hilt.Compiler)
        add("kaptTest", Hilt.AndroidXCompiler)
        add("kaptAndroidTest", Hilt.AndroidXCompiler)
        add("annotationProcessor", Hilt.AndroidXCompiler)

    }

    fun DependencyHandler.implementAndroidXTest() {
        implementAll(Espresso.IdlingResource)
        testImplementAll(
            AndroidXTest.Core,
            AndroidXTest.Rules,
        )
        androidTestImplementAll(
            AndroidXTest.Fragment,
            AndroidXTest.Butler,
            AndroidXTest.Core,
            AndroidXTest.Junit,
            AndroidXTest.Rules,
            AndroidXTest.Test
        )
    }

    fun DependencyHandler.implementUITest() {
        testImplementAll(
            AndroidXTest.Junit
        )

        androidTestImplementAll(
            AndroidXTest.Junit,
            Espresso.Contrib,
            Espresso.Intents,
            Espresso.Idling,
            Espresso.IdlingResource,
            Robolectric.Annotations,
        )
    }

    fun DependencyHandler.implementNetworking() {
        implementAll(
            OkHttp.OkHttp,
            Retrofit.Core,
            Retrofit.Gson,
            Retrofit.Rxjava,
            Retrofit.Scalars
        )
    }

    fun DependencyHandler.implementGson() {
        implementAll(
            Retrofit.Gson,
        )
    }

    fun DependencyHandler.implementTime() {
        implementAll(
            Joda.Core
        )
    }

    fun DependencyHandler.implementRoom() {
        implementAll(
            Room.Core,
            Room.Runtime,
            Room.Rxjava,
            Room.Paging
        )
        addAll("annotationProcessor", Room.Compiler)
        kaptImplementAll(Room.Compiler)
        androidTestImplementAll(Room.Test)
    }

    fun DependencyHandler.implementPaging() {
        implementAll(
            Paging.Runtime,
            Paging.Rxjava,
            Paging.Common
        )
    }

    fun DependencyHandler.implementMvi() {
        implementAll(
            MVI.Orbit
        )

        testImplementAll(MVI.OrbitTest)
    }

    object Kotlin {
        const val Stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val Reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val CoroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val CoroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val CoroutineRx =
            "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${Versions.coroutines}"
        const val AndroidX = "androidx.core:core-ktx:1.7.0"
        const val CoroutineTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object AndroidX {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val Runtime = "androidx.arch.core:core-runtime:2.1.0"
        const val Annotation = "androidx.annotation:annotation:${Versions.androidXAnnotations}"
        const val AndroidXLegacy =
            "androidx.legacy:legacy-support-v4:${Versions.androidXLegacySupport}"
        const val RecyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val Preference = "androidx.preference:preference-ktx:1.2.0"
        const val PreferenceLegacy = "androidx.legacy:legacy-preference-v14:1.0.0"
        const val Splash = "androidx.core:core-splashscreen:1.0.0-beta02"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
        const val Material = "com.google.android.material:material:${Versions.material}"
        const val Fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    }

    object AndroidXTest {
        const val Core = "androidx.test:core-ktx:${Versions.androidXTestCore}"
        const val Junit = "androidx.test.ext:junit-ktx:${Versions.androidXTestExtKotlinRunner}"
        const val Rules = "androidx.test:rules:${Versions.androidXTestRules}"
        const val Fragment = "androidx.fragment:fragment-testing:${Versions.fragment}"
        const val Test = "androidx.arch.core:core-testing:${Versions.archTesting}"
        const val Butler = "com.linkedin.testbutler:test-butler-library:2.2.1"
    }

    object Lifecycle {
        const val Runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val Reactivestreams =
            "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
        const val Livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val Viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val Java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    }

    object Navigation {
        const val Fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val UI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val Dynamic =
            "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
        const val Testing = "androidx.navigation:navigation-testing:${Versions.navigation}"
        const val Compose = "androidx.navigation:navigation-compose:2.4.1"
    }

    object MVI {
        const val Orbit = "org.orbit-mvi:orbit-viewmodel:${Versions.orbit}"
        const val OrbitTest = "org.orbit-mvi:orbit-test:${Versions.orbit}"
    }

    object Epoxy {
        const val Epoxy = "com.airbnb.android:epoxy:${Versions.epoxy}"
        const val Paging = "com.airbnb.android:epoxy-paging3:${Versions.epoxy}"
        const val Processor = "com.airbnb.android:epoxy-processor:${Versions.epoxy}"
    }

    object Testing {
        const val Junit = "junit:junit:${Versions.junit}"
        const val Truth = "com.google.truth:truth:${Versions.truth}"

        const val Mockk = "io.mockk:mockk:${Versions.mockk}"
        const val MockkJvm = "io.mockk:mockk-agent-jvm:${Versions.mockk}"
        const val MockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

        const val Turbine = "app.cash.turbine:turbine:0.8.0"
    }

    object Glide {
        const val Glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val Processor = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Hilt {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val Compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val Test = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

        //Hilt for AndroidX
        const val Fragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigation}"
        const val Work = "androidx.hilt:hilt-work:${Versions.hiltNavigation}"
        const val AndroidXCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltNavigation}"
    }

    object Robolectric {
        const val Core = "org.robolectric:robolectric:${Versions.robolectric}"
        const val Annotations = "org.robolectric:annotations:${Versions.robolectric}"
    }

    object Espresso {
        const val Core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val Contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val Intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
        const val Idling = "androidx.test.espresso.idling:idling-concurrent:${Versions.espresso}"
        const val IdlingResource =
            "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    }

    object Retrofit {
        const val Core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val Gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val Rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
        const val Scalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    }

    object OkHttp {
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val Mock = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    }

    object Room {
        const val Core = "androidx.room:room-ktx:${Versions.room}"
        const val Runtime = "androidx.room:room-runtime:${Versions.room}"
        const val Rxjava = "androidx.room:room-rxjava2:${Versions.room}"
        const val Paging = "androidx.room:room-paging:${Versions.room}"
        const val Compiler = "androidx.room:room-compiler:${Versions.room}"
        const val Test = "androidx.room:room-testing:${Versions.room}"
    }

    object Paging {
        const val Runtime = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
        const val Rxjava = "androidx.paging:paging-rxjava2-ktx:${Versions.paging}"
        const val Common = "androidx.paging:paging-common-ktx:${Versions.paging}"
    }

    object Joda {
        const val Core = "com.jakewharton.threetenabp:threetenabp:1.4.0"
        const val Gson = "org.aaronhe:threetenbp-gson-adapter:1.0.2"
        const val Test = "org.threeten:threetenbp:1.6.0"
    }

}
