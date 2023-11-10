package ru.net2fox.trackerapp.intent

sealed class TaskListIntent {
    data class CreateTaskList(val name: String) : TaskListIntent()
    object LoadTaskLists : TaskListIntent()
}