package com.pabvazzam.test.repository

import com.pabvazzam.test.data.Task

interface SharedPreferencesRepository {

    fun addTask(task: Task)
    fun getTasks(): List<Task>?
    fun getTask(id: String): Task
}