package com.example.marsrobots.nasa.mvi

import androidx.paging.PagingData
import com.example.marsrobots.nasa.models.NasaItem
import com.example.mvi.base.State
import kotlinx.coroutines.flow.Flow

data class NasaViewState(
    val items: Flow<PagingData<NasaItem>>? = null
) : State