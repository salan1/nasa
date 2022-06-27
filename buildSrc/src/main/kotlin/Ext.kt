import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementAll(vararg list: String) {
    list.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.implementAll(list: List<String>) {
    list.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.testImplementAll(vararg list: String) {
    list.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.testImplementAll(list: List<String>) {
    list.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.androidTestImplementAll(list: List<String>) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun DependencyHandler.androidTestImplementAll(vararg list: String) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun DependencyHandler.kaptImplementAll(vararg list: String) {
    list.forEach {
        add("kapt", it)
    }
}

fun DependencyHandler.kaptImplementAll(list: List<String>) {
    list.forEach {
        add("kapt", it)
    }
}

fun DependencyHandler.addAll(key: String, vararg list: String) {
    list.forEach {
        add(key, it)
    }
}
