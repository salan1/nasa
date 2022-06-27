package com.example.storage.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

@Entity
data class NasaEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: Instant
)