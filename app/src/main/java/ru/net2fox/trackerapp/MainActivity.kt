package ru.net2fox.trackerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.net2fox.trackerapp.data.repository.TaskListRepository
import ru.net2fox.trackerapp.data.repository.TaskRepository
import ru.net2fox.trackerapp.ui.navigation.NavGraph
import ru.net2fox.trackerapp.ui.task.TaskViewModel
import ru.net2fox.trackerapp.ui.task.TaskViewModelFactory
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModel
import ru.net2fox.trackerapp.ui.tasklist.TaskListViewModelFactory
import ru.net2fox.trackerapp.ui.theme.TrackerAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("YATT")
                                }
                            )
                        }
                    ) { innerPadding ->
                        NavGraph(application as TrackerApp, innerPadding)
                    }

                }
            }
        }
    }
}