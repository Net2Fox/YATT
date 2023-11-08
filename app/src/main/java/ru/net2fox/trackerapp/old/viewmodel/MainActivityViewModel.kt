package ru.net2fox.trackerapp.old.viewmodel

import ru.net2fox.trackerapp.old.TrackerRepository
import ru.net2fox.trackerapp.old.database.ListOfTasks

class MainActivityViewModel {
    private val trackerRepository = TrackerRepository.get()
    private val listsLiveData = trackerRepository.getLists()

    fun deleteList(list: ListOfTasks) {
        trackerRepository.deleteList(list)
    }

}