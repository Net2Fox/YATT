package ru.net2fox.trackerapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import ru.net2fox.trackerapp.databinding.FragmentListBinding
import ru.net2fox.trackerapp.databinding.FragmentTaskBinding

private const val KEY_LIST_NAME = "ru.net2fox.trackerapp.LIST_NAME"

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_task, container, false)
        val view = binding.root

        binding.text.text = arguments?.getString(KEY_LIST_NAME) ?: throw IllegalStateException()

        return view
    }

    companion object {
        fun newInstance(param1: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_LIST_NAME, param1)
                }
            }
    }
}