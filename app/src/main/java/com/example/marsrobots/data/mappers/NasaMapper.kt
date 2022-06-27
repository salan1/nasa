package com.example.marsrobots.data.mappers

import com.example.api.dto.NasaItemDto
import com.example.storage.entities.NasaEntity
import javax.inject.Inject

/**
 * Mapper of DTO to Entity
 */
class NasaMapper @Inject constructor() : DtoMapper<NasaItemDto, NasaEntity> {

    override fun transformDto(dto: NasaItemDto): NasaEntity {
        val data = dto.data.first()
        val url = dto.links.first().href

        return NasaEntity(
            id = data.nasaId,
            title = data.title,
            description = data.description,
            date = data.dateCreated,
            imageUrl = url
        )
    }
}