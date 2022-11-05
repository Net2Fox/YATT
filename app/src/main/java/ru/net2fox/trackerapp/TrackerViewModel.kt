package ru.net2fox.trackerapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.net2fox.trackerapp.database.ListOfTasks
import ru.net2fox.trackerapp.database.ListsTasks
import ru.net2fox.trackerapp.database.Task

class TrackerViewModel : ViewModel() {

    private val trackerRepository = TrackerRepository.get()
    val listsWithTasksLiveData = trackerRepository.getListsWithTasks()

    fun getListById(id: Int): ListsTasks? = listsWithTasksLiveData.value?.get(id)
    fun getListId(position: Int): Int? = listsWithTasksLiveData.value?.get(position)?.list?.listId
    fun contains(itemId: Int): Boolean = listsWithTasksLiveData.value?.contains(getListById(itemId)) ?: false
    fun createIdSnapshot(): List<ListsTasks?> = (0 until listSize!!).map { position -> getListById(position) }
    val listSize: Int? get() = listsWithTasksLiveData.value?.size


    fun addList(listName: String) {
        trackerRepository.insertList(ListOfTasks(name = listName))
    }

    fun updateList(list: ListOfTasks) {
        trackerRepository.updateList(list)
    }

    fun addTask(taskName: String, ownerId: Int) {
        trackerRepository.insertTask(Task(listOwnerId = ownerId, name = taskName))
    }

    fun updateTask(task: Task) {
        trackerRepository.updateTask(task)
    }
}