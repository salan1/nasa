package com.example.marsrobots.di.core


import android.app.Application
import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.threeten.bp.ZoneId
import java.util.*
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DispatcherModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application


    @Provides
    internal fun provideLocale(context: Context): Locale = Locale.getDefault()

    @Provides
    internal fun provideZoneId(): ZoneId = ZoneId.systemDefault()
}
