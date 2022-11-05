package ru.net2fox.trackerapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface TrackerDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE taskId=(:id)")
    fun getTask(id: Int): LiveData<Task?>

    @Transaction
    @Query("SELECT * FROM ListOfTasks")
    fun getListsWithTasks() : LiveData<List<ListsTasks>>

    @Transaction
    @Query("SELECT * FROM ListOfTasks WHERE listId=(:id)")
    fun getListWithTasksById(id: Int) : LiveData<ListsTasks>

    @Update
    fun updateList(list: ListOfTasks)

    @Insert
    fun insertList(list: ListOfTasks)

    @Update
    fun updateTask(task: Task)

    @Insert
    fun insertTask(task: Task)
}