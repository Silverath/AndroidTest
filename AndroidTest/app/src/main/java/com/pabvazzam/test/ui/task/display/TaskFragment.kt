package com.pabvazzam.test.ui.task.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    private fun initRecyclerView() {
        viewModel.fetchTasks()
        val manager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.taskRv.layoutManager = manager
        binding.taskRv.adapter =
            TaskAdapter(viewModel.uiState.value.tasks) { task -> onTaskClicked(task) }
        binding.taskRv.addItemDecoration(decoration)
    }

    private fun onTaskClicked(task: Task) {
        val action = TaskFragmentDirections.actionNavigationTaskToNavigationEditTask(
            viewModel.convertTaskToJson(task)
        )
        binding.root.findNavController()
            .navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        initRecyclerView()
        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_task_to_navigation_add_task)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}