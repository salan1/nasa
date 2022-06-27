package com.example.api.clients

import com.example.api.services.INasaService
import com.google.gson.Gson

class NasaClient constructor(
    gson: Gson
) : BaseApiClient(
    ApiClientConfig(), gson
) {
    var api: INasaService = configRetrofit(
        INasaService::class.java,
        host
    )

    companion object {
        private const val host = "https://images-api.nasa.gov/"
    }
}