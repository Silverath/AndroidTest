package com.pabvazzam.test.ui.task.edit

import com.pabvazzam.test.data.Task

sealed interface EditTaskUiState {

    object Loading : EditTaskUiState

    object Error : EditTaskUiState

    data class Success(
        val task: Task,
        val title: String = task.title,
        val description: String = task.description,
        val expirationDate: String = task.expirationDate,
        val status: String = task.status,
        val selectDateError: Boolean = false
    ) : EditTaskUiState
}

