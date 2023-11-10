package ru.net2fox.trackerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.net2fox.trackerapp.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE listId = :listId")
    fun getTasksForList(listId: Int): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
}