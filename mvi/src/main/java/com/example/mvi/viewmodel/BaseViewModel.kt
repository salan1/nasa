package com.example.mvi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvi.base.Effect
import com.example.mvi.base.Reducer
import com.example.mvi.base.SideEffect
import com.example.mvi.base.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel<STATE : State, EFFECT : Effect, SIDE_EFFECT : SideEffect>(
    val reducer: Reducer<STATE, EFFECT, SIDE_EFFECT>,
    protected val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {

    val state: STATE get() = container.stateFlow.value
    protected abstract fun reporter(error: Throwable)

    protected abstract val initialState: Lazy<STATE>
    override val container by lazy { container<STATE, SIDE_EFFECT>(this.initialState.value) }
}

