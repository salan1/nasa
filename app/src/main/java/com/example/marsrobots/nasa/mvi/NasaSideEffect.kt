package com.example.marsrobots.nasa.mvi

import com.example.mvi.base.SideEffect

sealed class NasaSideEffect : SideEffect {
    data class DoSomethingWithItem(val itemHeading: String) : NasaSideEffect()
}
