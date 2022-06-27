package com.example.marsrobots.nasa.domain

import android.util.Log
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.marsrobots.data.repositories.NasaRepository
import com.example.marsrobots.nasa.models.NasaItem
import com.example.marsrobots.nasa.mvi.NasaEffect
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

/**
 * Interactor mapping Data/Domain layer models into view specific models
 */
class NasaInteractor @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val localZoneID: ZoneId,
    private val locale: Locale,
    private val dispatcher: CoroutineDispatcher
) {

    private val shortDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern(DATE_FORMAT).withLocale(locale)
    }

    /**
     * Retrieve [Flow] of [PagingData]
     */
    fun nasaImages(scope: CoroutineScope): NasaEffect =
        NasaEffect.NasaItemsLoaded(
            nasaRepository.nasaImages()
                .map { pagingData ->
                    pagingData.map { entity ->
                        NasaItem(
                            entity.id,
                            entity.title,
                            entity.description,
                            getLocalDate(entity.date),
                            entity.imageUrl
                        )
                    }
                }.flowOn(dispatcher)
                .cachedIn(scope)
                .catch { error ->
                    Log.wtf(NasaInteractor::class.java.name, error)
                }
        )

    /**
     * Usually in a date utils with correct INTL usage
     */
    private fun getLocalDate(instant: Instant): String {
        val localTime = LocalDateTime.ofInstant(instant, localZoneID)
        return "${localTime.dayOfMonth}${formatDayOfMonthToSuffix(localTime.dayOfMonth)} ${
            localTime.format(
                shortDateFormatter
            )
        }"
    }

    /**
     * @param n day in month
     */
    private fun formatDayOfMonthToSuffix(n: Int): String {
        if (Locale.ENGLISH.language != locale.language) {
            return ""
        }
        return if (n in DAY_OF_MONTH_ELEVENTH..DAY_OF_MONTH_THIRTEENTH) {
            "th"
        } else when (n % TEN) {
            DAY_OF_MONTH_FIRST -> "st"
            DAY_OF_MONTHS_SECOND -> "nd"
            DAY_OF_MONTH_THIRD -> "rd"
            else -> "th"
        }
    }

    companion object {
        const val DATE_FORMAT = "MMM yyyy"
        private const val TEN = 10
        private const val DAY_OF_MONTH_ELEVENTH = 11
        private const val DAY_OF_MONTH_THIRTEENTH = 13
        private const val DAY_OF_MONTH_FIRST = 1
        private const val DAY_OF_MONTHS_SECOND = 2
        private const val DAY_OF_MONTH_THIRD = 3
    }
}
