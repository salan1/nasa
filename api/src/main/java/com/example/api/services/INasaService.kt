package com.example.api.services

import com.example.api.dto.NasaDto
import retrofit2.http.GET
import retrofit2.http.Query

interface INasaService {

    @GET("search?q=mars&media_type=image")
    suspend fun images(
        @Query("page") page: Int = 1
    ): NasaDto
}