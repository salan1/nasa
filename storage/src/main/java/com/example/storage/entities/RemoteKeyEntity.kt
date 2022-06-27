package com.example.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * Remote keys used to determine page positions
 * @property type page request type
 * @property nextKey next key to be used in paged requests
 */
@Entity
data class RemoteKeyEntity(
    @PrimaryKey
    val type: String,
    val nextKey: Int?,
)