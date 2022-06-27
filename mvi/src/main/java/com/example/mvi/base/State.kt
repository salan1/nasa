package com.example.mvi.base

interface State

fun <S : State, E : SideEffect> S.reduceEffect(sideEffect: E? = null): ReduceEffect<S, E> =
    ReduceEffect(this, sideEffect)