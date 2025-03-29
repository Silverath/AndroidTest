package com.pabvazzam.test.usecase

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.SharedPreferencesRepositoryImpl
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val taskRepositoryImpl: SharedPreferencesRepositoryImpl
) {

    operator fun invoke(task: Task): Result<Boolean> {
        try {
            taskRepositoryImpl.deleteTask(task)
            return Result.success(true)
        } catch (error: Error) {
            return Result.failure(exception = NullPointerException())
        }
    }
}