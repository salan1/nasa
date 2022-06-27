package com.example.marsrobots.nasa.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.marsrobots.nasa.domain.NasaInteractor
import com.example.marsrobots.nasa.mvi.*
import com.example.mvi.viewmodel.DeepStreamViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * MVI based [ViewModel] that I setup for Deepfinity and based off Orbit-MVI
 */
@HiltViewModel
class NasaViewModel @Inject constructor(
    private val interactor: NasaInteractor,
    reducer: NasaReducer,
    dispatcher: CoroutineDispatcher
) : DeepStreamViewModel<NasaViewState, NasaIntent, NasaEffect, NasaSideEffect>(
    reducer,
    dispatcher
) {

    override val initialState: Lazy<NasaViewState> = lazy { NasaViewState() }
    override val initialIntent = NasaIntent.Setup

    override suspend fun handleIntent(intent: NasaIntent): Flow<NasaEffect> =
        when (intent) {
            NasaIntent.Setup -> flowOf(interactor.nasaImages(viewModelScope))
            is NasaIntent.ProcessItem -> flowOf(NasaEffect.ItemTapped(intent.item))
        }

    override fun reporter(error: Throwable) {
        Log.wtf(NasaViewModel::class.java.name, error)
    }
}
