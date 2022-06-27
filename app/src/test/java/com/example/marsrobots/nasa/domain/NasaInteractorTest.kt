package com.example.marsrobots.nasa.domain

import androidx.paging.PagingData
import com.example.marsrobots.data.repositories.NasaRepository
import com.example.marsrobots.nasa.models.NasaItem
import com.example.marsrobots.nasa.mvi.NasaEffect
import com.example.marsrobots.utils.collectData
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import nasaEntity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.ZoneId
import java.io.IOException
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class NasaInteractorTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    val repo = mockk<NasaRepository>()

    lateinit var localZoneID: ZoneId
    lateinit var locale: Locale

    lateinit var interactor: NasaInteractor

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        localZoneID = ZoneId.of("UTC")
        locale = Locale.ENGLISH

        interactor = NasaInteractor(repo, localZoneID, locale, testDispatcher)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        Dispatchers.resetMain()
    }

    /**
     * Note that tests can be created for more timezones
     */
    @Test
    fun nasaImagesShouldReturnMappedPagingDataFlowEffect() = testScope.runTest {
        // given
        val pagedData = PagingData.from(listOf(nasaEntity))
        every { repo.nasaImages() } returns flowOf(pagedData)

        // when
        val effect = interactor.nasaImages(testScope)

        // then
        val expectedItem = NasaItem(
            nasaEntity.id,
            nasaEntity.title,
            nasaEntity.description,
            "31st Oct 2008",
            nasaEntity.imageUrl
        )

        assertThat(effect).isInstanceOf(NasaEffect.NasaItemsLoaded::class.java)
        val mappedItems = (effect as NasaEffect.NasaItemsLoaded).flow.collectData()
        assertThat(mappedItems).isEqualTo(listOf(expectedItem))
    }
}
