package com.example.mvi.base

interface SideEffect


fun <S : State, E : SideEffect> E.reduceEffect(state: S): ReduceEffect<S, E> =
    ReduceEffect(state, this)