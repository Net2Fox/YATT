package ru.net2fox.trackerapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.net2fox.trackerapp.data.dao.TaskDao
import ru.net2fox.trackerapp.data.dao.TaskListDao
import ru.net2fox.trackerapp.data.model.Task
import ru.net2fox.trackerapp.data.model.TaskList

@Database(entities = [Task::class, TaskList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskListDao(): TaskListDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yatt_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}