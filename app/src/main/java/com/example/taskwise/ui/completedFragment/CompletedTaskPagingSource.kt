package com.example.taskwise.ui.completedFragment

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.taskwise.data.model.Task
import com.example.taskwise.data.repository.CompletedTaskRepository


class CompletedTaskPagingSource(private val repository: CompletedTaskRepository) :
    PagingSource<Int, Task>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Task> {
        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize

        val tasks = repository.getAllCompletedTasksSynchronously() // LiveData'yı almak yerine direkt listeyi döndüren bir fonksiyon kullanmalısınız.

        return LoadResult.Page(
            data = tasks,
            prevKey = if (pageNumber == 1) null else pageNumber - 1,
            nextKey = if (tasks.isEmpty()) null else pageNumber + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Task>): Int? {
        TODO("Not yet implemented")
    }
}


