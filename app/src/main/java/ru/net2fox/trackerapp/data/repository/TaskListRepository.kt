package ru.net2fox.trackerapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.net2fox.trackerapp.data.dao.TaskListDao
import ru.net2fox.trackerapp.data.model.TaskList

class TaskListRepository(private val taskListDao: TaskListDao) {

    fun getAllTaskLists(): Flow<List<TaskList>> = taskListDao.getAllTaskLists()

    suspend fun insertTaskList(taskList: TaskList) {
        taskListDao.insertTaskList(taskList)
    }
}