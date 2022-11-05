package ru.net2fox.trackerapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class ListsTasks(
    @Embedded var list: ListOfTasks,
    @Relation(
        parentColumn = "listId",
        entityColumn = "listOwnerId"
    ) var tasks: List<Task>
) {
}