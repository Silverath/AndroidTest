package com.pabvazzam.test.data

import com.pabvazzam.test.TASK_STATUS_PENDING

data class Task(
    val title: String,
    val description: String,
    val expirationDate: String,
    val status: String = TASK_STATUS_PENDING,
)
