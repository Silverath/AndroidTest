package com.pabvazzam.test.usecase

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.SharedPreferencesRepositoryImpl
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepositoryImpl: SharedPreferencesRepositoryImpl
) {

    operator fun invoke(): Result<List<Task>> {
        val tasks = taskRepositoryImpl.getTasks()
        return if (tasks == null) {
            Result.failure(exception = NullPointerException())
        } else {
            Result.success(tasks)
        }
    }
}