package com.example.api.dto

import com.google.gson.annotations.SerializedName


data class CollectionDto(
    @SerializedName("version")
    val version: String,
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<NasaItemDto>,
    @SerializedName("links")
    val links: List<LinksDto>,
)