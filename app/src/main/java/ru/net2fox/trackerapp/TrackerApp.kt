package ru.net2fox.trackerapp

import android.app.Application
import androidx.room.Room
import ru.net2fox.trackerapp.data.AppDatabase

class TrackerApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "yatt-database"
        ).build()
    }
}