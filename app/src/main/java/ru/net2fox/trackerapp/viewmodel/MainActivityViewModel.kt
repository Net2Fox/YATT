package ru.net2fox.trackerapp.viewmodel

import ru.net2fox.trackerapp.TrackerRepository
import ru.net2fox.trackerapp.database.ListOfTasks

class MainActivityViewModel {
    private val trackerRepository = TrackerRepository.get()
    private val listsLiveData = trackerRepository.getLists()

    fun deleteList(list: ListOfTasks) {
        trackerRepository.deleteList(list)
    }

}