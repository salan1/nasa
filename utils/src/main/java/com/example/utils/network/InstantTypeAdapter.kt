package com.example.utils.network

import com.google.gson.JsonPrimitive
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.threeten.bp.Instant


class InstantTypeAdapter : TypeAdapter<Instant>() {

    override fun write(out: JsonWriter, value: Instant) {
        out.value(JsonPrimitive(value.toEpochMilli()).toString())
    }

    override fun read(input: JsonReader): Instant = Instant.parse(input.nextString())
}