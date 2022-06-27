package com.example.marsrobots.nasa.mvi

import com.example.marsrobots.nasa.models.NasaItem
import com.example.mvi.base.Intent

sealed class NasaIntent : Intent {
    object Setup : NasaIntent()
    data class ProcessItem(val item: NasaItem) : NasaIntent()
}