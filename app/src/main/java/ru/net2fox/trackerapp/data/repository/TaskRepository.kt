package ru.net2fox.trackerapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.net2fox.trackerapp.data.dao.TaskDao
import ru.net2fox.trackerapp.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    fun getTasksForList(listId: Int): Flow<List<Task>> = taskDao.getTasksForList(listId)

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}