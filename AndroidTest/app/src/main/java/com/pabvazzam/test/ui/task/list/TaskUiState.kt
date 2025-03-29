package com.pabvazzam.test.ui.task.list

import com.pabvazzam.test.data.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList()
)
