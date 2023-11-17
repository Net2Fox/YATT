package ru.net2fox.trackerapp.ui.tasklist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.net2fox.trackerapp.data.model.TaskList
import ru.net2fox.trackerapp.intent.TaskListIntent
import ru.net2fox.trackerapp.ui.navigation.Screen

@Composable
fun TaskListScreen(viewModel: TaskListViewModel, navController: NavController, innerPadding: PaddingValues) {
    val taskLists by viewModel.taskLists.observeAsState(listOf())
    var openAlertDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(innerPadding)) {
        Button(onClick = { openAlertDialog = true }) {
            Text(text = "Add Task List")
        }
        LazyColumn {
            items(taskLists) {taskList ->
                TaskListItem(taskList = taskList, onTaskListClicked = { navController.navigate(
                    Screen.TaskScreen.createRoute(taskList.id)) })
            }
        }
    }

    when {
        openAlertDialog -> {
            AddTaskListDialog(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false
                    viewModel.handleIntent(TaskListIntent.CreateTaskList(it))
                }
            )
        }
    }
}

@Composable
fun TaskListItem(taskList: TaskList, onTaskListClicked: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onTaskListClicked() }) {
        Text(text = taskList.name, modifier = Modifier.padding(16.dp))
    }
}
