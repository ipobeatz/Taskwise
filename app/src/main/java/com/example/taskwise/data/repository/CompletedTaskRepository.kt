package com.example.taskwise.data.repository

import android.app.Application
import com.example.taskwise.data.dao.CompletedTaskDao
import com.example.taskwise.data.database.CompletedTaskDataBase.Companion.invoke
import com.example.taskwise.data.model.Task

class CompletedTaskRepository(application: Application) {

    private val dao: CompletedTaskDao by lazy {
        val database = invoke(application)
        database.getCompletedTaskDao()
    }

    fun getAllCompletedTasks() = dao.getAllCompletedTasks()

    suspend fun insertCompletedTask(task: Task) = dao.insertCompletedTask(task)

    suspend fun deleteCompletedTask(task: Task) = dao.deleteCompletedTask(task)

    suspend fun deleteAllCompletedTasks() = dao.deleteAllCompletedTasks()

    fun getAllCompletedTasksSynchronously(): List<Task> {
        return dao.getAllCompletedTasksSynchronously()
    }
}
