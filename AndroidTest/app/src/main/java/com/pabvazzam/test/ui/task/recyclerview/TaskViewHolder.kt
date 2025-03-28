package com.pabvazzam.test.ui.task.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pabvazzam.test.data.Task
import com.pabvazzam.test.databinding.RvItemTaskBinding

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var _binding: RvItemTaskBinding = RvItemTaskBinding.bind(view)

    private val binding get() = _binding

    fun render(task: Task, onTaskClicked: (Task) -> Unit) {
        binding.rvItemTaskTitle.text = task.title
        binding.rvItemTaskDescription.text = task.description
        binding.rvItemTaskExpirationDate.text = task.expirationDate
        binding.rvItemTaskStatus.text = task.status
        binding.rvItemTaskContainer.setOnClickListener {
            onTaskClicked(task)
        }
    }
}