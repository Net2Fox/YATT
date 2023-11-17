package ru.net2fox.trackerapp.ui.tasklist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskListDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (listName: String) -> Unit
) {
    var listName by remember { mutableStateOf("") }

    AlertDialog(
        icon = {
            Icon(Icons.Default.List, contentDescription = null)
        },
        title = {
            Text(text = "Enter a name for the list")
        },
        text = {
            TextField(
                value = listName,
                onValueChange = {
                    listName = it
                },
                label = {
                    Text(text = "List Name")
                },
                placeholder = {
                    Text(text = "List #1")
                }
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(listName)
                },
                enabled = listName.isNotBlank()
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}