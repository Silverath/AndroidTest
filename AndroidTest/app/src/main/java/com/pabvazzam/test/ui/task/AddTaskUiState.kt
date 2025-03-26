package com.pabvazzam.test.ui.task

data class AddTaskUiState(
    val title: String = "",
    val description: String = "",
    val expirationDate: String = "",
    val selectDateError: String = ""
)