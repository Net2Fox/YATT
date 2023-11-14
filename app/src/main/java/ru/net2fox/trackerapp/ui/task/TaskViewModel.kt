package ru.net2fox.trackerapp.ui.task

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import ru.net2fox.trackerapp.data.model.Task
import ru.net2fox.trackerapp.data.repository.TaskRepository
import ru.net2fox.trackerapp.intent.TaskIntent

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun handleIntent(intent: TaskIntent) {
        when (intent) {
            is TaskIntent.AddTask -> addTask(intent.task)
            is TaskIntent.UpdateTask -> updateTask(intent.task)
            is TaskIntent.LoadTasks -> loadTasks(intent.listId)
            is TaskIntent.ToggleTaskCompletion -> toggleTaskCompletion(intent.task)
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            loadTasks(task.listId) // Refresh the tasks after adding
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
            loadTasks(task.listId) // Refresh the tasks after updating
        }
    }

    private fun loadTasks(listId: Int) {
        viewModelScope.launch {
            repository.getTasksForList(listId).collect {
                _tasks.postValue(it)
            }
        }
    }

    private fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            repository.updateTask(updatedTask)
        }
    }
}