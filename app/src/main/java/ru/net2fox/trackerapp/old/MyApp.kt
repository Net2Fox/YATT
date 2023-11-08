package ru.net2fox.trackerapp.old

import android.app.Application
import com.google.android.material.color.DynamicColors

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        TrackerRepository.initialize(this)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}