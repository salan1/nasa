package com.example.storage.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant

/**
 * Convert needed to store Instant date into DB
 */
object DateTimeConverters {

    @TypeConverter
    @JvmStatic
    fun instantToLong(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

    @TypeConverter
    @JvmStatic
    fun longToInstant(millis: Long?): Instant? = millis?.let {
        Instant.ofEpochMilli(millis)
    }
}
