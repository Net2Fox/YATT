package ru.net2fox.trackerapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.net2fox.trackerapp.data.model.TaskList

@Dao
interface TaskListDao {
    @Query("SELECT * FROM task_lists")
    fun getAllTaskLists(): Flow<List<TaskList>>

    @Insert
    suspend fun insertTaskList(taskList: TaskList)
}