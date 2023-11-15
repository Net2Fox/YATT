package ru.net2fox.trackerapp.ui.navigation

import android.app.Application
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.net2fox.trackerapp.TrackerApp
import ru.net2fox.trackerapp.data.repository.TaskListRepository
import ru.net2fox.trackerapp.data.repository.TaskRepository
import ru.net2fox.trackerapp.ui.task.AddTaskScreen
import ru.net2fox.trackerapp.ui.task.TaskScreen
import ru.net2fox.trackerapp.ui.task.TaskViewModel
import ru.net2fox.trackerapp.ui.task.TaskViewModelFactory
import ru.net2fox.trackerapp.ui.tasklist.AddTaskListScreen
import ru.net2fox.trackerapp.ui.tasklist.TaskListScreen
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModel
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModelFactory

sealed class Screen(val route: String) {
    object TaskListScreen : Screen("taskListScreen")
    object AddTaskListScreen : Screen("addTaskListScreen")
    object TaskScreen : Screen("taskScreen/{listId}") {
        fun createRoute(listId: Int) = "taskScreen/$listId"
    }
    object AddTaskScreen : Screen("addTaskScreen/{listId}") {
        fun createRoute(listId: Int) = "addTaskScreen/$listId"
    }
}

@Composable
fun NavGraph(application: TrackerApp, innerPadding: PaddingValues, startDestination: String = Screen.TaskListScreen.route, ) {
    val navController = rememberNavController()
    val taskListDao = application.database.taskListDao()
    val taskDao = application.database.taskDao()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.TaskListScreen.route) {
            val taskListViewModel: TaskListViewModel = viewModel(factory = TaskListViewModelFactory(TaskListRepository(taskListDao)))
            TaskListScreen(taskListViewModel, navController, innerPadding)
        }
        composable(Screen.AddTaskListScreen.route) {
            val taskListViewModel: TaskListViewModel = viewModel(factory = TaskListViewModelFactory(TaskListRepository(taskListDao)))
            AddTaskListScreen(taskListViewModel, navController, innerPadding)
        }
        composable(Screen.TaskScreen.route, arguments = listOf(navArgument("listId") { type = NavType.IntType })) { backStackEntry ->
            val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(TaskRepository(taskDao)))
            TaskScreen(taskViewModel, navController, backStackEntry.arguments?.getInt("listId") ?: -1, innerPadding)
        }
        composable(Screen.AddTaskScreen.route, arguments = listOf(navArgument("listId") { type = NavType.IntType })) { backStackEntry ->
            val taskViewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(TaskRepository(taskDao)))
            AddTaskScreen(taskViewModel, navController, backStackEntry.arguments?.getInt("listId") ?: -1, innerPadding)
        }
    }
}
