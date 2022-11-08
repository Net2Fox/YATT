package ru.net2fox.trackerapp

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.net2fox.trackerapp.viewmodel.ListsViewModel

class ViewPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private val listsViewModel: ListsViewModel) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): TaskFragment {
        val listId = listsViewModel.getListById(position)?.list?.listId
        return TaskFragment.newInstance(listId!!)
    }

    override fun getItemCount(): Int = listsViewModel.listSize ?: 0
    override fun getItemId(position: Int): Long = listsViewModel.getListId(position)?.toLong()!!
    override fun containsItem(itemId: Long): Boolean = listsViewModel.contains(itemId.toInt())
}