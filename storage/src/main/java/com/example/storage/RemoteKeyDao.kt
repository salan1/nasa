package com.example.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storage.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: RemoteKeyEntity)

    @Query("SELECT * FROM RemoteKeyEntity WHERE type == :type")
    suspend fun key(type: String): RemoteKeyEntity

    @Query("DELETE FROM RemoteKeyEntity")
    suspend fun clearAll()
}
