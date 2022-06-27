package com.example.marsrobots.data.repositories

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.api.dto.CollectionDto
import com.example.api.dto.LinksDto
import com.example.api.dto.NasaDto
import com.example.api.dto.NasaItemDto
import com.example.api.services.INasaService
import com.example.marsrobots.data.mappers.NasaMapper
import com.example.marsrobots.data.repositories.NasaRemoteMediator.Companion.NASA_KEY_NEXT
import com.example.marsrobots.data.repositories.NasaRemoteMediator.Companion.REMOTE_KEY
import com.example.storage.AppDatabase
import com.example.storage.entities.NasaEntity
import com.example.storage.entities.RemoteKeyEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nasaDto
import nasaEntity
import nasaItemDto
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class NasaRemoteMediatorTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    val client = mockk<INasaService>()
    val mapper = mockk<NasaMapper>()

    private lateinit var database: AppDatabase

    lateinit var nasaRemoteMediator: NasaRemoteMediator

    @Before
    fun setUp() {
        createDb()
        nasaRemoteMediator = NasaRemoteMediator(database, client, mapper)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun loadShouldReturnSuccessResultWhenMoreDataIsPresent() = runTest {
        // given
        val entity = nasaEntity

        val pagingState = PagingState<Int, NasaEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        coEvery { client.images() } returns nasaDto
        every { mapper.transformDto(nasaItemDto) } returns entity

        // when
        val result = nasaRemoteMediator.load(LoadType.REFRESH, pagingState)
        val key = database.remoteKeys().key(REMOTE_KEY)
        val storedItems = database.nasaDao().get()

        // then
        coVerify { client.images() }
        assertThat(key).isEqualTo(RemoteKeyEntity(REMOTE_KEY, null))
        assertThat(storedItems).isEqualTo(listOf(entity))

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun loadShouldReturnSuccessResultWhenNoDataIsPresent() = runTest {
        // given
        val collectionDto = CollectionDto(
            version = "",
            href = "",
            items = listOf(),
            links = listOf()
        )
        val nasaDto = NasaDto(collection = collectionDto)
        coEvery { client.images() } returns nasaDto

        val pagingState = PagingState<Int, NasaEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        // when
        val result = nasaRemoteMediator.load(LoadType.REFRESH, pagingState)
        val key = database.remoteKeys().key(REMOTE_KEY)
        val storedItems = database.nasaDao().get()

        // then
        coVerify { client.images() }
        assertThat(key).isEqualTo(RemoteKeyEntity(REMOTE_KEY, null))
        assertThat(storedItems).isEqualTo(listOf<NasaEntity>())

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun loadShouldReturnSuccessResultWhenMoreDataIsAvailable() = runTest {
        // given
        val collection = mockkClass(CollectionDto::class)
        val items = (1..50).map {
            mockkClass(NasaItemDto::class)
        }
        every { collection.items } returns items
        val link = LinksDto(
            rel = NASA_KEY_NEXT,
            prompt = "",
            href = ""
        )
        every { collection.links } returns listOf(link)

        val nasaDto = NasaDto(collection = collection)

        coEvery { client.images() } returns nasaDto

        val entities = (1..50).map { index ->
            nasaEntity.copy(id = index.toString())
        }

        items.forEachIndexed { index, item ->
            every { mapper.transformDto(item) } returns entities[index]
        }

        val pagingState = PagingState<Int, NasaEntity>(
            listOf(),
            null,
            PagingConfig(5),
            1
        )

        // when
        val result = nasaRemoteMediator.load(LoadType.REFRESH, pagingState)
        val key = database.remoteKeys().key(REMOTE_KEY)
        val storedItems = database.nasaDao().get()

        // then
        coVerify { client.images() }
        assertThat(key).isEqualTo(RemoteKeyEntity(REMOTE_KEY, 2))
        assertThat(storedItems).containsExactlyElementsIn(entities)

        assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isFalse()
    }
}