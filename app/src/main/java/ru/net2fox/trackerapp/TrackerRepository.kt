package ru.net2fox.trackerapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import ru.net2fox.trackerapp.database.TrackerDatabase
import java.util.UUID
import kotlin.collections.List

private const val DATABASE_NAME = "tracker-database"

class TrackerRepository private constructor(context: Context) {
    private val database : TrackerDatabase = Room.databaseBuilder(
        context.applicationContext,
        TrackerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val trackerDao = database.trackerDao()

    fun getTasks(): LiveData<List<Task>> = trackerDao.getTasks()
    fun getTask(id: Int): LiveData<Task?> = trackerDao.getTask(id)

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