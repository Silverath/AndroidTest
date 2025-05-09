package com.pabvazzam.test.ui.task.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pabvazzam.test.R
import com.pabvazzam.test.data.Task

class TaskAdapter(private var tasks: List<Task>, private val onTaskClicked: (Task) -> Unit) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.render(task, onTaskClicked)
    }

    override fun getItemCount(): Int = tasks.size

    fun updateTasks(filtered: List<Task>) {
        val tasksDiff = TaskDiffUtil(tasks, filtered)
        val finalResult = DiffUtil.calculateDiff(tasksDiff)
        tasks = filtered
        finalResult.dispatchUpdatesTo(this)
    }
}