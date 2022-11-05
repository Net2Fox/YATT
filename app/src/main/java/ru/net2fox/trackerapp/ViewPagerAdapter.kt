package ru.net2fox.trackerapp

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private val trackerViewModel: TrackerViewModel) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): TaskFragment {
        val name = trackerViewModel.getListById(position)?.list?.listId
        return TaskFragment.newInstance(name!!)
    }

    override fun getItemCount(): Int = trackerViewModel.listSize ?: 0
    //override fun getItemId(position: Int): Long = trackerViewModel.getListId(position)?.toLong()!!
    //override fun containsItem(itemId: Long): Boolean = trackerViewModel.contains(itemId.toInt())
}