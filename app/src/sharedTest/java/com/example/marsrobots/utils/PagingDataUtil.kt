package com.example.marsrobots.utils

import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalCoroutinesApi
@Suppress("RestrictedApi")
suspend fun <T : Any> Flow<PagingData<T>>.collectData(): List<T> {
    val differCallback = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) = Unit
        override fun onInserted(position: Int, count: Int) = Unit
        override fun onRemoved(position: Int, count: Int) = Unit
    }

    val items = mutableListOf<T>()

    return suspendCoroutine { continuation ->
        val differ = object : PagingDataDiffer<T>(differCallback) {

            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                lastAccessedIndex: Int,
                onListPresentable: () -> Unit
            ): Int? {
                for (i in 0 until newList.size) {
                    items.add(newList.getFromStorage(i))
                }

                onListPresentable()
                continuation.resume(items)

                return null
            }
        }

        TestScope().launch {
            collectLatest {
                differ.collectFrom(it)
            }
        }
    }
}

class NoopListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
    override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
    override fun onInserted(position: Int, count: Int) = Unit
    override fun onRemoved(position: Int, count: Int) = Unit
}

class DiffCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}

fun <T : Any> createPagingDiffer(
    dispatcher: CoroutineDispatcher = Dispatchers.Main
) = AsyncPagingDataDiffer<T>(
    diffCallback = DiffCallback(),
    updateCallback = NoopListCallback(),
    workerDispatcher = dispatcher
)
