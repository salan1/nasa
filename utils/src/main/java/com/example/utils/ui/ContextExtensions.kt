package com.example.utils.ui

import android.content.res.Resources

object ContextExtensions {
    val Int.dpToPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}