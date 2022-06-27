package com.example.storage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storage.entities.NasaEntity

@Dao
interface NasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: NasaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<NasaEntity>)

    @Query("SELECT * FROM nasaentity")
    suspend fun get(): List<NasaEntity>

    @Query("SELECT * FROM nasaentity")
    fun pagingSource(): PagingSource<Int, NasaEntity>

    @Query("DELETE FROM NasaEntity")
    suspend fun clearAll()
}
