package ru.net2fox.trackerapp.ui.task

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
import ru.net2fox.trackerapp.data.model.Task
import ru.net2fox.trackerapp.intent.TaskIntent
import ru.net2fox.trackerapp.intent.TaskListIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(viewModel: TaskViewModel, navController: NavController, listId: Int, innerPadding: PaddingValues) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (Optional)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val task = Task(title = title, description = description, listId = listId)
                viewModel.handleIntent(TaskIntent.AddTask(task))
                navController.popBackStack()
            },
            enabled = title.isNotBlank()
        ) {
            Text("Add Task")
        }
    }
}
