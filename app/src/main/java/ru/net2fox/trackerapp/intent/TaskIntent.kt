package ru.net2fox.trackerapp.intent

import ru.net2fox.trackerapp.data.model.Task

sealed class TaskIntent {
    data class AddTask(val task: Task) : TaskIntent()
    data class UpdateTask(val task: Task) : TaskIntent()
    data class LoadTasks(val listId: Int) : TaskIntent()
    data class ToggleTaskCompletion(val task: Task) : TaskIntent()
}