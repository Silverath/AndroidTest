package com.pabvazzam.test.ui.task.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabvazzam.test.R
import com.pabvazzam.test.TASK_STATUS_DONE
import com.pabvazzam.test.TASK_STATUS_PENDING
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null

    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by viewModels()

    private fun initRecyclerView() {
        viewModel.fetchTasks()
        val manager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, manager.orientation)
        taskAdapter = TaskAdapter(viewModel.uiState.value.tasks) { task -> onTaskClicked(task) }
        binding.taskRv.layoutManager = manager
        binding.taskRv.adapter = taskAdapter
        binding.taskRv.addItemDecoration(decoration)
    }

    private fun onTaskClicked(task: Task) {
        val action = TaskFragmentDirections.actionNavigationTaskToNavigationEditTask(
            task.id
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
        binding.taskChipPendingFilter.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFilterByStatus(
                isChecked, TASK_STATUS_PENDING
            )
        }
        binding.taskChipCompletedFilter.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFilterByStatus(
                isChecked, TASK_STATUS_DONE
            )
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    taskAdapter.updateTasks(state.tasks)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}