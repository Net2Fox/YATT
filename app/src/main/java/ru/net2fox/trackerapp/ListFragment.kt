package ru.net2fox.trackerapp

import android.app.ActivityManager.TaskDescription
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.net2fox.trackerapp.databinding.FragmentListBinding
import kotlin.collections.List

private const val KEY_SELECTED_TAB_INDEX = "ru.net2fox.trackerapp.SELECTED_TAB_INDEX"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ViewPagerAdapter

    // Лениво инициализируем ViewModel
    private val trackerViewModel: TrackerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_list, container, false)
        val view = binding.root

        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, trackerViewModel)
        binding.viewPager.adapter = adapter

        fun changeDataSet(performChanges: () -> Unit) {
            performChanges()
            binding.viewPager.adapter!!.notifyDataSetChanged()
            binding.viewPager.setCurrentItem(trackerViewModel.size, false)
        }

        // Просулшивание клика на FloatingButton (кнопка +)
        binding.fab.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle(R.string.create_list_dialog_title)
            builder.setView(R.layout.create_list_alertdialog)
            builder.setPositiveButton(R.string.ok_dialog_button,
                DialogInterface.OnClickListener { dialog, which ->
                    val listNmaeEditText: TextInputEditText? = (dialog as AlertDialog).findViewById(R.id.editText)
                    // Добавление новой вкладки
                    changeDataSet { trackerViewModel.addNew(listNmaeEditText?.text.toString()) }
                })
            builder.setNegativeButton(R.string.cancel_dialog_button,
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = trackerViewModel.getItemById(position).name
        }.attach()

        binding.tabs.selectTab(binding.tabs.getTabAt(savedInstanceState?.getInt(KEY_SELECTED_TAB_INDEX) ?: 0))

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_TAB_INDEX, binding.tabs.selectedTabPosition)
    }
}



private const val KEY_ITEM_TEXT = "androidx.viewpager2.integration.testapp.KEY_ITEM_TEXT"
private const val KEY_CLICK_COUNT = "androidx.viewpager2.integration.testapp.KEY_CLICK_COUNT"

class PageFragment : Fragment() {
    private lateinit var textViewItemText: TextView
    private lateinit var textViewCount: TextView
    private lateinit var buttonCountIncrease: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_viewpager2, container, false)

        textViewItemText = view.findViewById(R.id.textViewItemText)
        textViewCount = view.findViewById(R.id.textViewCount)
        buttonCountIncrease = view.findViewById(R.id.buttonCountIncrease)

        textViewItemText.text = arguments?.getString(KEY_ITEM_TEXT) ?: throw IllegalStateException()
        Log.d("TrackerApp", "${savedInstanceState?.getInt(KEY_CLICK_COUNT) ?: 0}")
        fun updateCountText(count: Int) {
            textViewCount.text = "$count"
        }
        updateCountText(savedInstanceState?.getInt(KEY_CLICK_COUNT) ?: 0)

        buttonCountIncrease.setOnClickListener {
            updateCountText(clickCount() + 1)
        }

        return view
    }

    /**
     * [FragmentStateAdapter] minimizes the number of [Fragment]s kept in memory by saving state of
    [Fragment]s that are no longer near the viewport. Here we demonstrate this behavior by relying
    on it to persist click counts through configuration changes (rotation) and data-set changes
    (when items are added or removed).
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_CLICK_COUNT, clickCount())
    }

    private fun clickCount(): Int {
        return "${textViewCount.text}".toInt()
    }

    companion object {
        fun create(itemText: String) =
            PageFragment().apply {
                arguments = Bundle(1).apply {
                    putString(KEY_ITEM_TEXT, itemText)
                }
            }
    }
}