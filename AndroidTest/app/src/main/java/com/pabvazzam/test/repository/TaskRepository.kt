package com.pabvazzam.test.repository

import com.pabvazzam.test.data.Task

interface TaskRepository {

    fun addTask(task: Task)
    fun getTasks(): List<Task>?
    fun getTask(id: String): Task
    fun editTask(task: Task)
    fun deleteTask(task: Task)
}