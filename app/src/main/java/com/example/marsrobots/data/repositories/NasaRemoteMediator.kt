package com.example.marsrobots.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.api.services.INasaService
import com.example.marsrobots.data.mappers.NasaMapper
import com.example.storage.AppDatabase
import com.example.storage.entities.NasaEntity
import com.example.storage.entities.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Paging Mediator loading live data from the network and serving into the local db
 * for offline usage
 */
@OptIn(ExperimentalPagingApi::class)
class NasaRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val client: INasaService,
    private val mapper: NasaMapper
) : RemoteMediator<Int, NasaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NasaEntity>
    ): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    database.withTransaction {
                        database.nasaDao().clearAll()
                        database.remoteKeys().add(RemoteKeyEntity(REMOTE_KEY, 1))
                    }
                    1
                }
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remoteKeys().key(REMOTE_KEY).nextKey
                    } ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    remoteKey
                }
            }

            val response = client.images(page)
            val nextLink = response.collection.links.firstOrNull {
                it.rel == NASA_KEY_NEXT
            }
            val nextPage = if (nextLink != null) page + 1 else null


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.nasaDao().clearAll()
                }

                database.remoteKeys().add(
                    RemoteKeyEntity(REMOTE_KEY, nextPage)
                )

                val items = response.collection.items.map(mapper::transformDto)
                database.nasaDao().insertAll(items)
            }

            MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        const val REMOTE_KEY = "nasa"
        const val NASA_KEY_NEXT = "next"
    }
}
