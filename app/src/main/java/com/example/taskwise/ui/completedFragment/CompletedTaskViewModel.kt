package com.example.taskwise.ui.completedFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taskwise.data.model.Task
import com.example.taskwise.data.repository.CompletedTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompletedTaskViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val repository: CompletedTaskRepository = CompletedTaskRepository(application)
    val getAllCompletedTasks: LiveData<List<Task>> = repository.getAllCompletedTasks()

    fun insertCompletedTask(task: Task) {
        viewModelScope.launch {
            repository.insertCompletedTask(task)
        }
    }

    fun deleteCompletedTask(task: Task) {
        viewModelScope.launch {
            repository.deleteCompletedTask(task)
        }
    }

    fun deleteAllCompletedTasks() {
        viewModelScope.launch {
            repository.deleteAllCompletedTasks()
        }
    }
}