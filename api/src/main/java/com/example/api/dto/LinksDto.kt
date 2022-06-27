package com.example.api.dto

import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("rel")
    val rel: String,
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("href")
    val href: String
)