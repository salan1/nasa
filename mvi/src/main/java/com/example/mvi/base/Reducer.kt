package com.example.mvi.base


abstract class Reducer<STATE : State, EFFECT : Effect, SIDE_EFFECT : SideEffect> {
    abstract suspend fun reduce(state: STATE, effect: EFFECT): ReduceEffect<STATE, SIDE_EFFECT>
}

class ReduceEffect<STATE : State, SIDE_EFFECT : SideEffect>(
    val state: STATE,
    val sideEffect: SIDE_EFFECT? = null
)
