package com.example.marsrobots.di.core

import com.example.api.clients.NasaClient
import com.example.api.services.INasaService
import com.example.utils.network.InstantTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.Instant
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Instant::class.java, InstantTypeAdapter())
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideClient(
        gson: Gson
    ): INasaService = NasaClient(
        gson
    ).api

}
