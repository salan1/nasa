package com.example.marsrobots.data.mappers

interface DtoMapper<in T, out F> {
    fun transformDto(dto: T): F
}