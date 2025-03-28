package com.pabvazzam.test.ui.task

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.databinding.FragmentTaskBinding
import com.pabvazzam.test.ui.task.recyclerview.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {

                }
            }
        }
    }

    private fun initRecyclerView() {
        viewModel.fetchTasks()
        binding.taskRv.layoutManager = LinearLayoutManager(context)
        binding.taskRv.adapter =
            TaskAdapter(viewModel.uiState.value.tasks) { task -> onTaskClicked(task) }
    }

    private fun onTaskClicked(task: Task) {
        
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