package ru.net2fox.trackerapp.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.net2fox.trackerapp.data.model.Task
import ru.net2fox.trackerapp.intent.TaskIntent
import ru.net2fox.trackerapp.ui.navigation.Screen

@Composable
fun TaskScreen(viewModel: TaskViewModel, navController: NavController, listId: Int, innerPadding: PaddingValues) {
    val tasks by viewModel.tasks.observeAsState(listOf())

    LaunchedEffect(key1 = listId) {
        viewModel.handleIntent(TaskIntent.LoadTasks(listId))
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        Button(onClick = { navController.navigate(Screen.AddTaskScreen.createRoute(listId)) }) {
            Text(text = "Add Task")
        }

        LazyColumn {
            items(tasks) { task ->
                TaskItem(task = task, onTaskClicked = { viewModel.handleIntent(TaskIntent.ToggleTaskCompletion(task)) })
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskClicked: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onTaskClicked() }
        )
        Text(text = task.title, modifier = Modifier.padding(start = 8.dp))
    }
}
