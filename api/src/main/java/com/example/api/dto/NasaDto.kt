package com.example.api.dto

import com.google.gson.annotations.SerializedName

data class NasaDto(
    @SerializedName("collection")
    val collection: CollectionDto
)