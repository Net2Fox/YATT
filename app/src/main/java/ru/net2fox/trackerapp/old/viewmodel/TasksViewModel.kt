package ru.net2fox.trackerapp.old.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.net2fox.trackerapp.old.TrackerRepository
import ru.net2fox.trackerapp.old.database.ListsTasks
import ru.net2fox.trackerapp.old.database.Task

class TasksViewModel() : ViewModel() {

    private val trackerRepository = TrackerRepository.get()
    private val listIdLiveData = MutableLiveData<Int>()

    val listTasksLiveData: LiveData<ListsTasks> =
        Transformations.switchMap(listIdLiveData) { listId ->
            trackerRepository.getListWithTasks(listId)
        }

    fun loadTasks(listId: Int) {
        listIdLiveData.value = listId
    }

    fun saveTask(task: Task) {
        trackerRepository.updateTask(task)
    }
}