package com.example.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.mvi.base.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

abstract class DeepStreamViewModel<STATE : State, INTENT : Intent, EFFECT : Effect, SIDE_EFFECT : SideEffect>(
    reducer: Reducer<STATE, EFFECT, SIDE_EFFECT>,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<STATE, EFFECT, SIDE_EFFECT>(reducer, dispatcher) {

    open protected val initialIntent: INTENT? = null

    protected abstract suspend fun handleIntent(intent: INTENT): Flow<EFFECT>

    fun dispatchInitialIntent() = initialIntent?.also { dispatch(it) }

    fun dispatch(
        intent: INTENT
    ) = intent {
        viewModelScope.launch {
            withContext(dispatcher) {
                handleIntent(intent)
                    .map { effect -> reducer.reduce(this@DeepStreamViewModel.state, effect) }
                    .flowOn(dispatcher)
                    .catch { err ->
                        reporter(err)
                    }
                    .collect { result ->
                        try {
                            if (result.sideEffect != null) this@intent.postSideEffect(result.sideEffect)
                            reduce {
                                result.state
                            }
                        } catch (err: Exception) {
                            reporter(err)
                        }
                    }
            }
        }
    }
}