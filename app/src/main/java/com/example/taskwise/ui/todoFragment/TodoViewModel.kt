package com.example.taskwise.ui.todoFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskwise.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(application)
    val getAllTasks: LiveData<List<com.example.taskwise.data.model.Task>> = repository.getAllTasks()

    fun insertTask(task: com.example.taskwise.data.model.Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    fun deleteTask(task: com.example.taskwise.data.model.Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}