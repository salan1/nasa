package com.example.marsrobots.data.mappers

interface EntityMapper<in T, out F> {
    fun transformEntity(entity: T): F
}