package ru.net2fox.trackerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.net2fox.trackerapp.ui.task.AddTaskScreen
import ru.net2fox.trackerapp.ui.task.TaskScreen
import ru.net2fox.trackerapp.ui.task.TaskViewModel
import ru.net2fox.trackerapp.ui.tasklist.AddTaskListScreen
import ru.net2fox.trackerapp.ui.tasklist.TaskListScreen
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModel

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
fun NavGraph(taskListViewModel: TaskListViewModel, taskViewModel: TaskViewModel, startDestination: String = Screen.TaskListScreen.route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.TaskListScreen.route) {
            TaskListScreen(taskListViewModel, navController)
        }
        composable(Screen.AddTaskListScreen.route) {
            AddTaskListScreen(taskListViewModel, navController)
        }
        composable(Screen.TaskScreen.route, arguments = listOf(navArgument("listId") { type = NavType.IntType })) { backStackEntry ->
            TaskScreen(taskViewModel, navController, backStackEntry.arguments?.getInt("listId") ?: -1)
        }
        composable(Screen.AddTaskScreen.route, arguments = listOf(navArgument("listId") { type = NavType.IntType })) { backStackEntry ->
            AddTaskScreen(taskViewModel, navController, backStackEntry.arguments?.getInt("listId") ?: -1)
        }
    }
}
