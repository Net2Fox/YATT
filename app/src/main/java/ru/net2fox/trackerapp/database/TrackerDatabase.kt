package ru.net2fox.trackerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ ru.net2fox.trackerapp.Task::class ], version = 1)
abstract class TrackerDatabase : RoomDatabase() {
    abstract fun trackerDao() : TrackerDao
}