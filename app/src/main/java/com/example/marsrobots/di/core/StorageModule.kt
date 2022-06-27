package com.example.marsrobots.di.core

import android.content.Context
import androidx.room.Room
import com.example.storage.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Singleton
    @Provides
    internal fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .build()


    companion object {
        private const val DB_NAME = "offlineDB"
    }
}