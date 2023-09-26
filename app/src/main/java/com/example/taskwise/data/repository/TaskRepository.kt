package com.example.taskwise.data.repository

import android.app.Application
import com.example.taskwise.data.dao.TaskDao
import com.example.taskwise.data.database.TaskDataBase.Companion.invoke
import com.example.taskwise.data.model.Task

class TaskRepository(application: Application) {

    private val dao: TaskDao by lazy {
        val database = invoke(application)
        database.getTaskDao()
    }

    fun getAllTasks() = dao.getAllTasks()

    suspend fun insertTask(task: Task) = dao.insertTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

    suspend fun deleteAllTasks() = dao.deleteAllTasks()
}
