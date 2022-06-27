package com.example.utils.ui

import android.app.Activity
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Used to implement [ViewBinding] property delegate for [Activity].
 *
 * @param viewBindingBind a lambda function that creates a [ViewBinding] instance from [Activity]'s contentView, eg: `T::bind` static method can be used.
 * @param viewBindingClazz if viewBindingBind is not provided, Kotlin Reflection will be used to get `T::bind` static method.
 */
public class ActivityViewBindingDelegate<T : ViewBinding> private constructor(
    viewBindingBind: ((View) -> T)? = null,
    viewBindingClazz: Class<T>? = null
) : ReadOnlyProperty<Activity, T> {

    private var binding: T? = null
    private val bind = viewBindingBind ?: { view: View ->
        @Suppress("UNCHECKED_CAST")
        GetBindMethod(viewBindingClazz!!)(null, view) as T
    }

    init {
        ensureMainThread()
        require(viewBindingBind != null || viewBindingClazz != null) {
            "Both viewBindingBind and viewBindingClazz are null. Please provide at least one."
        }
    }

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        return binding
            ?: bind(thisRef.findViewById<ViewGroup>(android.R.id.content).getChildAt(0))
                .also { binding = it }
    }

    public companion object Factory {
        /**
         * Create [ActivityViewBindingDelegate] from [viewBindingBind] lambda function.
         *
         * @param viewBindingBind a lambda function that creates a [ViewBinding] instance from [Activity]'s contentView, eg: `T::bind` static method can be used.
         */
        public fun <T : ViewBinding> from(viewBindingBind: (View) -> T): ActivityViewBindingDelegate<T> =
            ActivityViewBindingDelegate(viewBindingBind = viewBindingBind)

        /**
         * Create [ActivityViewBindingDelegate] from [ViewBinding] class.
         *
         * @param viewBindingClazz Kotlin Reflection will be used to get `T::bind` static method from this class.
         */
        public fun <T : ViewBinding> from(viewBindingClazz: Class<T>): ActivityViewBindingDelegate<T> =
            ActivityViewBindingDelegate(viewBindingClazz = viewBindingClazz)
    }
}

/**
 * Create [ViewBinding] property delegate for this [Activity].
 * @param bind a lambda function that creates a [ViewBinding] instance from [Activity]'s contentView, eg: `T::bind` static method can be used.
 */
@Suppress("unused")
@MainThread
public fun <T : ViewBinding> AppCompatActivity.viewBinding(bind: (View) -> T): ActivityViewBindingDelegate<T> =
    ActivityViewBindingDelegate.from(viewBindingBind = bind)

/**
 * Create [ViewBinding] property delegate for this [Activity].
 */
@Suppress("unused")
@MainThread
public inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(): ActivityViewBindingDelegate<T> =
    ActivityViewBindingDelegate.from(viewBindingClazz = T::class.java)


@PublishedApi
internal fun ensureMainThread(): Unit = check(Looper.getMainLooper() == Looper.myLooper()) {
    "Expected to be called on the main thread but was " + Thread.currentThread().name
}

private const val debug = false

internal inline fun log(crossinline message: () -> String) {
    if (debug) {
        Log.d("ViewBinding", message())
    }
}

internal object GetBindMethod {
    init {
        ensureMainThread()
    }

    private val methodSignature = View::class.java
    private val methodMap = ArrayMap<Class<out ViewBinding>, Method>()

    internal operator fun <T : ViewBinding> invoke(clazz: Class<T>): Method =
        methodMap
            .getOrPut(clazz) { clazz.getMethod("bind", methodSignature) }
            .also { log { "GetBindMethod::methodMap.size: ${methodMap.size}" } }
}
