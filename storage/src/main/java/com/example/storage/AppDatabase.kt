package com.example.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.storage.converters.DateTimeConverters
import com.example.storage.entities.NasaEntity
import com.example.storage.entities.RemoteKeyEntity

@Database(
    entities = [NasaEntity::class, RemoteKeyEntity::class],
    version = 1,
    autoMigrations = [],
    exportSchema = true
)
@TypeConverters(
    DateTimeConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nasaDao(): NasaDao
    abstract fun remoteKeys(): RemoteKeyDao
}