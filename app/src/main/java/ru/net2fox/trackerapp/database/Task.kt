package ru.net2fox.trackerapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var taskId: Int = 0,
    var listOwnerId: Int,
    var name: String,
    var description: String? = null,
    var isDone: Boolean = false
) {
}