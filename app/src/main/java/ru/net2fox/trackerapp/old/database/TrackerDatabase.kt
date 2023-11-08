package ru.net2fox.trackerapp.old.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ Task::class, ListOfTasks::class ], version = 1, exportSchema = true)
abstract class TrackerDatabase : RoomDatabase() {
    abstract fun trackerDao() : TrackerDao
}