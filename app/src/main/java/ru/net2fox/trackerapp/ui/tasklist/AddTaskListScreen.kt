package ru.net2fox.trackerapp.ui.tasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.net2fox.trackerapp.intent.TaskListIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListScreen(viewModel: TaskListViewModel, navController: NavController, innerPadding: PaddingValues) {
    var listName by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
        TextField(
            value = listName,
            onValueChange = { listName = it },
            label = { Text("List Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.handleIntent(TaskListIntent.CreateTaskList(listName))
                navController.popBackStack()
            },
            enabled = listName.isNotBlank()
        ) {
            Text("Create Task List")
        }
    }
}
