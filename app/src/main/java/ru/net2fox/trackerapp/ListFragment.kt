package ru.net2fox.trackerapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
        binding.fab.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle(R.string.create_task_dialog_title)
            val dialogView: View = inflater.inflate(R.layout.create_alertdialog, null, false)
            dialogView.findViewById<TextInputLayout>(R.id.textInputLayout).hint = getString(R.string.create_task_hint)
            builder.setView(dialogView)
            builder.setPositiveButton(R.string.ok_dialog_button, null)
            builder.setNegativeButton(R.string.cancel_dialog_button, null)
            val alertDialog: AlertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val taskNameEditText: TextInputEditText? = dialogView.findViewById(R.id.editText)
                val wantToCloseDialog: Boolean = taskNameEditText?.text.toString().trim().isEmpty()
                // Если EditText пуст, отключите закрытие при нажатии на позитивную кнопку
                if (!wantToCloseDialog) {
                    alertDialog.dismiss()
                    trackerViewModel.getListId(binding.tabs.selectedTabPosition)
                        ?.let { trackerViewModel.addTask(taskNameEditText?.text.toString(), it) }
                }
            }
        }
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = trackerViewModel.getListById(position)?.list?.name
        }.attach()
        binding.tabs.selectTab(binding.tabs.getTabAt(savedInstanceState?.getInt(KEY_SELECTED_TAB_INDEX) ?: 0))
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.create_list_tab))
        setTouchListenerToTab()
        trackerViewModel.listsWithTasksLiveData.observe(
            viewLifecycleOwner,
            Observer { listTasks ->
                listTasks?.let {
                    changeDataSet({  })
                }
            }
        )
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeDataSet(performChanges: () -> Unit, isNewListAdd: Boolean = false) {
        performChanges()
        binding.viewPager.adapter!!.notifyDataSetChanged()
        if(isNewListAdd)
            binding.viewPager.setCurrentItem(trackerViewModel.listSize ?: 0, false)
        binding.tabs.addTab(binding.tabs.newTab().setText(R.string.create_list_tab))
        setTouchListenerToTab()
        //if(binding.tabs.size <= 1) {
        //    binding.fab.visibility = View.INVISIBLE
        //}
        //else if(binding.tabs.size > 1) {
        //    binding.fab.visibility = View.VISIBLE
        //}
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListenerToTab() {
        val tabStrip = binding.tabs.getChildAt(0)
        if (tabStrip is ViewGroup) {
            val childCount = tabStrip.childCount
            for (i in 0 until childCount) {
                val tabView = tabStrip.getChildAt(i)
                tabView.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP){
                        if(i == binding.tabs.tabCount - 1) {
                            val builder = MaterialAlertDialogBuilder(requireContext())
                            builder.setTitle(R.string.create_list_dialog_title)
                            val dialogView: View = layoutInflater.inflate(R.layout.create_alertdialog, null, false)
                            builder.setView(dialogView)
                            builder.setPositiveButton(R.string.ok_dialog_button, null)
                            builder.setNegativeButton(R.string.cancel_dialog_button, null)
                            val alertDialog: AlertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                                val listNameEditText: TextInputEditText? = dialogView.findViewById(R.id.editText)
                                val wantToCloseDialog: Boolean = listNameEditText?.text.toString().trim().isEmpty()
                                // Если EditText пуст, отключите закрытие при нажатии на позитивную кнопку
                                if (!wantToCloseDialog) {
                                    alertDialog.dismiss()
                                    trackerViewModel.addList(listNameEditText?.text.toString())
                                }
                            }
                            return@setOnTouchListener true
                        }
                    }
                    return@setOnTouchListener false
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_TAB_INDEX, binding.tabs.selectedTabPosition)
    }
}