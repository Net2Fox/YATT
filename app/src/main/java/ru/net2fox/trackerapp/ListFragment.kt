package ru.net2fox.trackerapp

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import ru.net2fox.trackerapp.databinding.FragmentListBinding

private const val KEY_SELECTED_TAB_INDEX = "ru.net2fox.trackerapp.SELECTED_TAB_INDEX"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: ViewPagerAdapter

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