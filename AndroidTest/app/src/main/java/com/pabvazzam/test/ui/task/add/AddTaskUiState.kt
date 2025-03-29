package com.pabvazzam.test.ui.task.add

data class AddTaskUiState(
    val title: String = "",
    val description: String = "",
    val expirationDate: String = "",
    val selectDateError: Boolean = false
)