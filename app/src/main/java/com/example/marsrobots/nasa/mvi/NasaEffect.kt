package com.example.marsrobots.nasa.mvi

import androidx.paging.PagingData
import com.example.marsrobots.nasa.models.NasaItem
import com.example.mvi.base.Effect
import kotlinx.coroutines.flow.Flow

sealed class NasaEffect : Effect {
    data class NasaItemsLoaded(val flow: Flow<PagingData<NasaItem>>) : NasaEffect()
    data class ItemTapped(val item: NasaItem) : NasaEffect()
}