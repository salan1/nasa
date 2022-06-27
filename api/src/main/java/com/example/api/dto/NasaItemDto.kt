package com.example.api.dto

import com.google.gson.annotations.SerializedName

data class NasaItemDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("data")
    val data: List<NasaDataItemDto>,
    @SerializedName("links")
    val links: List<NasaItemLinkDto>
)