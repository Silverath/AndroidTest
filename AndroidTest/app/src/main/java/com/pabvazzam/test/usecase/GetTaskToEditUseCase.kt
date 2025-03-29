package com.pabvazzam.test.usecase

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.SharedPreferencesRepositoryImpl
import javax.inject.Inject

class GetTaskToEditUseCase @Inject constructor(
    private val taskRepositoryImpl: SharedPreferencesRepositoryImpl
) {

    operator fun invoke(id: String): Result<Task> {
        val task = taskRepositoryImpl.getTask(id)
        return Result.success(task)
    }
}