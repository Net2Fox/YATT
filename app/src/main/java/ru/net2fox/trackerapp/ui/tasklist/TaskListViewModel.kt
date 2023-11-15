package ru.net2fox.trackerapp.ui.tasklist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import ru.net2fox.trackerapp.data.model.TaskList
import ru.net2fox.trackerapp.data.repository.TaskListRepository
import ru.net2fox.trackerapp.intent.TaskListIntent

class TaskListViewModel(private val repository: TaskListRepository) : ViewModel() {

    private val _taskLists = MutableLiveData<List<TaskList>>()
    val taskLists: LiveData<List<TaskList>> = _taskLists

    init {
        loadTaskLists()
    }

    fun handleIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.CreateTaskList -> createTaskList(intent.name)
            is TaskListIntent.LoadTaskLists -> loadTaskLists()
        }
    }

    private fun createTaskList(name: String) {
        viewModelScope.launch {
            val taskList = TaskList(name = name)
            repository.insertTaskList(taskList)
        }
    }

    private fun loadTaskLists() {
        viewModelScope.launch {
            repository.getAllTaskLists().collect {
                _taskLists.postValue(it)
            }
        }
    }
}