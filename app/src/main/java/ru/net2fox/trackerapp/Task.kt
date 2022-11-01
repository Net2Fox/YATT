package ru.net2fox.trackerapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

//@Entity
//data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(), var taskName: String, var taskDescription: String, var isDone: Boolean = false)

@Entity
data class Task(
    @PrimaryKey var id: Int,
    var name: String,
    var description: String,
    var isDone: Boolean
) {
}