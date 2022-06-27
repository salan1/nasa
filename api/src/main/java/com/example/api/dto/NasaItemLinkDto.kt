package com.example.api.dto

import com.google.gson.annotations.SerializedName

data class NasaItemLinkDto(
    @SerializedName("href")
    val href: String,
    @SerializedName("rel")
    val rel: String,
    @SerializedName("render")
    val render: String
)