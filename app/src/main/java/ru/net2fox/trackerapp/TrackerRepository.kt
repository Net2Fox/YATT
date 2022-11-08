package ru.net2fox.trackerapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ru.net2fox.trackerapp.database.ListOfTasks
import ru.net2fox.trackerapp.database.ListsTasks
import ru.net2fox.trackerapp.database.Task
import ru.net2fox.trackerapp.database.TrackerDatabase
import java.util.concurrent.Executors
import kotlin.collections.List

private const val DATABASE_NAME = "tracker-database"

class TrackerRepository private constructor(context: Context) {
    private val database: TrackerDatabase = Room.databaseBuilder(
        context.applicationContext,
        TrackerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val trackerDao = database.trackerDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getTasks(): LiveData<List<Task>> = trackerDao.getTasks()
    fun getTask(id: Int): LiveData<Task?> = trackerDao.getTask(id)

    fun getLists(): LiveData<List<ListOfTasks>> = trackerDao.getLists()
    fun getList(id: Int): LiveData<ListOfTasks?> = trackerDao.getList(id)

    fun getListsWithTasks(): LiveData<List<ListsTasks>> = trackerDao.getListsWithTasks()

    fun getListWithTasks(id: Int) : LiveData<ListsTasks> = trackerDao.getListWithTasks(id)

    fun updateList(list: ListOfTasks) {
        executor.execute {
            trackerDao.updateList(list)
        }
    }

    fun insertList(list: ListOfTasks) {
        executor.execute {
            trackerDao.insertList(list)
        }
    }

    fun deleteList(list: ListOfTasks) {
        executor.execute {
            trackerDao.deleteList(list)
        }
    }

    fun deleteListWithTasks(list: ListOfTasks, tasks: List<Task>) {
        executor.execute {
            trackerDao.deleteListWithTasks(list, tasks)
        }
    }

    fun updateTask(task: Task) {
        executor.execute {
            trackerDao.updateTask(task)
        }
    }

    fun insertTask(task: Task) {
        executor.execute {
            trackerDao.insertTask(task)
        }
    }

    fun deleteTask(task: Task) {
        executor.execute {
            trackerDao.deleteTask(task)
        }
    }

    companion object {
        private var INSTANCE: TrackerRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TrackerRepository(context)
            }
        }

        fun get(): TrackerRepository {
            return INSTANCE ?:
            throw IllegalStateException("TrackerRepository must be initialized")
        }
    }
}