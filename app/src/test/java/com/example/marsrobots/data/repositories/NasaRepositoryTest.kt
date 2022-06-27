package com.example.marsrobots.data.repositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.api.services.INasaService
import com.example.marsrobots.data.mappers.NasaMapper
import com.example.marsrobots.utils.collectData
import com.example.storage.AppDatabase
import com.example.storage.entities.NasaEntity
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NasaRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    val client = mockk<INasaService>()
    val mapper = mockk<NasaMapper>()

    private lateinit var database: AppDatabase
    private lateinit var remoteMediator: NasaRemoteMediator

    lateinit var nasaRepository: NasaRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        remoteMediator = NasaRemoteMediator(database, client, mapper)
        nasaRepository = NasaRepository(database, remoteMediator)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun nasaImagesShouldReturnPagingDataFlow() = testScope.runTest {
        // given
        coEvery { client.images() } returns nasaDto
        every { mapper.transformDto(nasaItemDto) } returns nasaEntity

        // when
        val flow = nasaRepository.nasaImages()

        // then
        val firstItems = flow.collectData()
        assertThat(firstItems).isEqualTo(listOf<NasaEntity>())
        val secondItems = flow.collectData()
        assertThat(secondItems).isEqualTo(listOf(nasaEntity))
    }
}
