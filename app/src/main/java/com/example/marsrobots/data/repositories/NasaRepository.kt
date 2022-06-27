package com.example.marsrobots.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.storage.AppDatabase
import com.example.storage.entities.NasaEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NasaRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val nasaRemoteMediator: NasaRemoteMediator
) {

    /**
     * Flow of paged [NasaEntity]
     * Uses mediator to fetch and store new data into local db
     * Flow source of truth is the DB
     */
    @OptIn(ExperimentalPagingApi::class)
    fun nasaImages(): Flow<PagingData<NasaEntity>> = Pager(
        config = PagingConfig(pageSize = 15),
        remoteMediator = nasaRemoteMediator
    ) {
        appDatabase.nasaDao().pagingSource()
    }.flow
}