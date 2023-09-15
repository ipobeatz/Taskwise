package com.example.taskwise.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskwise.data.model.Task


@Dao
interface CompletedTaskDao {

    @Query("SELECT * FROM Tasks")
    fun getAllCompletedTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Tasks")
    fun getAllCompletedTasksSynchronously(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedTask(task: Task)

    @Delete
    suspend fun deleteCompletedTask(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllCompletedTasks()
}