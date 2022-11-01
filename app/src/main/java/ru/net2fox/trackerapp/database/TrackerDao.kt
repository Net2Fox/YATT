package ru.net2fox.trackerapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.net2fox.trackerapp.Task

@Dao
interface TrackerDao {
    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>
    @Query("SELECT * FROM task WHERE id=(:id)")
    fun getTask(id: Int): LiveData<Task?>
}