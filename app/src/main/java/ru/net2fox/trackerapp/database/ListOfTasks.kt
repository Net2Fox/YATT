package ru.net2fox.trackerapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListOfTasks (
    @PrimaryKey(autoGenerate = true) var listId: Int = 0,
    var name: String
) {
}