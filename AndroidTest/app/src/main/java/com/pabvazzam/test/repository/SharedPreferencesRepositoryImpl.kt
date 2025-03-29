package com.pabvazzam.test.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pabvazzam.test.TASK_SAVED_LIST
import com.pabvazzam.test.data.Task
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SharedPreferencesRepository {

    override fun getTasks(): List<Task> {
        return gson.fromJson<List<Task>?>(
            sharedPreferences.getString(TASK_SAVED_LIST, null),
            object : TypeToken<List<Task>?>() {}.type
        ) ?: emptyList()
    }

    override fun getTask(id: String): Task {
        val tasksList = getTasks()
        return tasksList.single { task -> task.id == id }
    }

    override fun addTask(task: Task) {
        val tasks = getTasks()
        val listToSave =
            if (tasks.isEmpty())
                listOf(task)
            else
                listOf(task).plus(tasks)
        val json = gson.toJson(listToSave)
        sharedPreferences.edit { putString(TASK_SAVED_LIST, json) }
    }
}