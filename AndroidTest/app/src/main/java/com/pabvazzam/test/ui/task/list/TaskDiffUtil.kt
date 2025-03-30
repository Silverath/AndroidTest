package com.pabvazzam.test.ui.task.list

import androidx.recyclerview.widget.DiffUtil
import com.pabvazzam.test.data.Task

class TaskDiffUtil(
    private val oldTasks: List<Task>,
    private val newTasks: List<Task>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldTasks.size
    }

    override fun getNewListSize(): Int {
        return newTasks.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition].id == newTasks[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTasks[oldItemPosition] == newTasks[newItemPosition]
    }

}