package ru.net2fox.trackerapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class TrackerViewModel : ViewModel() {
    private var nextValue = 1
    private val lists = mutableListOf(
        ListTasks(nextValue++, "List 1"),
        ListTasks(nextValue++, "List 2"),
        ListTasks(nextValue++, "List 3")
    )


    fun getItemById(id: Int): ListTasks = lists[id]
    fun itemId(position: Int): Int = lists[position].id
    fun contains(itemId: Int): Boolean = lists.any { it.id == itemId }
    fun addNew(listName: String) = lists.add(ListTasks(nextValue++, listName))
    fun removeAt(position: Int) = lists.removeAt(position)
    val size: Int get() = lists.size
}