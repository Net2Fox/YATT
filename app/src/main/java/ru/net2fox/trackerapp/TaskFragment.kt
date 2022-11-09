package ru.net2fox.trackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.net2fox.trackerapp.database.Task
import ru.net2fox.trackerapp.databinding.FragmentTaskBinding
import ru.net2fox.trackerapp.viewmodel.TasksViewModel

private const val KEY_LIST_ID = "ru.net2fox.trackerapp.LIST_ID"

class TaskFragment : Fragment() {

    private lateinit var tasks: List<Task>

    private lateinit var binding: FragmentTaskBinding
    private lateinit var adapter: TaskRecyclerViewAdapter

    private val tasksViewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_task, container, false)
        val view = binding.root
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(context)
        //val divider = MaterialDividerItemDecoration(view.context, LinearLayoutManager.VERTICAL)
        //binding.recyclerViewTasks.addItemDecoration(divider)
        adapter = TaskRecyclerViewAdapter(tasksViewModel)
        binding.recyclerViewTasks.adapter = adapter
        tasksViewModel.listTasksLiveData.observe(
            viewLifecycleOwner,
            Observer { tasks ->
                tasks?.let {
                    this.tasks = tasks.tasks
                    changeDataSet {  }
                }
            }
        )
        val listId = arguments?.getInt(KEY_LIST_ID) ?: throw IllegalStateException()
        tasksViewModel.loadTasks(listId)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeDataSet(performChanges: () -> Unit) {
        performChanges()
        binding.recyclerViewTasks.adapter!!.notifyDataSetChanged()
        //setTouchListenerToItem()
    }

    fun setTouchListenerToItem() {

    }

    private inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private lateinit var task: Task

        private val checkBoxView: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            checkBoxView.setOnClickListener(this)
        }

        fun bind(task: Task) {
            this.task = task
            checkBoxView.text = task.name
            checkBoxView.isChecked = task.isDone
            //checkBoxView.setOnCheckedChangeListener(null)
            //checkBoxView.setOnCheckedChangeListener { buttonView, isChecked ->
            //    task.isDone = checkBoxView.isChecked
            //    tasksViewModel.saveTask(task)
            //}
        }

        override fun onClick(v: View) {
            task.isDone = checkBoxView.isChecked
            tasksViewModel.saveTask(task)
        }
    }

        private inner class TaskRecyclerViewAdapter(private val taskDetailViewModel: TasksViewModel) : RecyclerView.Adapter<TaskViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            return TaskViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = taskDetailViewModel.listTasksLiveData.value?.tasks?.get(position)
            if (task != null) {
                holder.bind(task)
            }
        }

        override fun getItemCount(): Int {
            return taskDetailViewModel.listTasksLiveData.value?.tasks?.size ?: 0
        }
    }

    companion object {
        fun newInstance(param1: Int) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_LIST_ID, param1)
                }
            }
    }
}