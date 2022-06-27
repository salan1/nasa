package com.example.marsrobots.data.mappers

import com.example.storage.entities.NasaEntity
import com.google.common.truth.Truth.assertThat
import dataItemDto
import linkDto
import nasaItemDto
import org.junit.Before
import org.junit.Test


class NasaMapperTest {

    private lateinit var mapper: NasaMapper

    @Before
    fun setUp() {
        mapper = NasaMapper()
    }

    @Test
    fun shouldMapNasaItemDto() {
        // given
        val itemDto = nasaItemDto

        // when
        val entity = mapper.transformDto(itemDto)

        // then
        val expectedEntity = NasaEntity(
            id = dataItemDto.nasaId,
            title = dataItemDto.title,
            description = dataItemDto.description,
            date = dataItemDto.dateCreated,
            imageUrl = linkDto.href
        )
        assertThat(entity).isEqualTo(expectedEntity)
    }
}