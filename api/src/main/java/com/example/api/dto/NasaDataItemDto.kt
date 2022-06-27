package com.example.api.dto

import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant

data class NasaDataItemDto(
    @SerializedName("nasa_id")
    val nasaId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("date_created")
    val dateCreated: Instant,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("center")
    val center: String
)