package ru.net2fox.trackerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_lists")
data class TaskList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String
)