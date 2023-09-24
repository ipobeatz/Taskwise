package com.example.taskwise.ui.todoFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.taskwise.data.model.Task
import com.example.taskwise.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = TaskRepository(application)


    private val _inserted = MutableLiveData<Boolean>()
    val inserted: MutableLiveData<Boolean>
        get() = _inserted


    fun getAllTasks() = liveData(Dispatchers.IO) {
        emit(repository.getAllTasks())
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            getAllTasks()
            _inserted.value = true
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            getAllTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}
