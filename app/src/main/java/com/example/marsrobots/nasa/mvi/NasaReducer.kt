package com.example.marsrobots.nasa.mvi

import com.example.mvi.base.ReduceEffect
import com.example.mvi.base.Reducer
import com.example.mvi.base.reduceEffect
import javax.inject.Inject


class NasaReducer @Inject constructor(
) : Reducer<NasaViewState, NasaEffect, NasaSideEffect>() {

    override suspend fun reduce(
        state: NasaViewState,
        effect: NasaEffect
    ): ReduceEffect<NasaViewState, NasaSideEffect> =
        when (effect) {
            is NasaEffect.NasaItemsLoaded -> state.copy(items = effect.flow).reduceEffect()
            is NasaEffect.ItemTapped -> NasaSideEffect.DoSomethingWithItem(effect.item.heading)
                .reduceEffect(state.copy())
        }
}