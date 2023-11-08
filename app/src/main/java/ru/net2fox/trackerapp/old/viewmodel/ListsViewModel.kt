package ru.net2fox.trackerapp.old.viewmodel

import androidx.lifecycle.ViewModel
import ru.net2fox.trackerapp.old.TrackerRepository
import ru.net2fox.trackerapp.old.database.ListOfTasks
import ru.net2fox.trackerapp.old.database.ListsTasks
import ru.net2fox.trackerapp.old.database.Task

class ListsViewModel : ViewModel() {

    private val trackerRepository = TrackerRepository.get()

    var listsWithTasksLiveData = trackerRepository.getListsWithTasks()

    fun getListById(id: Int): ListsTasks? = listsWithTasksLiveData.value?.get(id)
    fun getListByListId(listId: Int): ListsTasks? = listsWithTasksLiveData.value?.find { it -> it.list.listId == listId }
    fun getListId(position: Int): Int? = listsWithTasksLiveData.value?.get(position)?.list?.listId
    fun contains(itemId: Int): Boolean = listsWithTasksLiveData.value?.contains(getListByListId(itemId)) ?: false
    val listSize: Int? get() = listsWithTasksLiveData.value?.size


    fun addList(listName: String) {
        trackerRepository.insertList(ListOfTasks(name = listName))
    }

    fun updateList(list: ListOfTasks) {
        trackerRepository.updateList(list)
    }

    fun deleteListWithTasks(list: ListsTasks) {
        trackerRepository.deleteListWithTasks(list.list, list.tasks)
    }

    fun addTask(taskName: String, ownerId: Int) {
        trackerRepository.insertTask(Task(listOwnerId = ownerId, name = taskName))
    }

    fun updateTask(task: Task) {
        trackerRepository.updateTask(task)
    }
}