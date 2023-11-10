package ru.net2fox.trackerapp.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.net2fox.trackerapp.data.repository.TaskRepository
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModel

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}