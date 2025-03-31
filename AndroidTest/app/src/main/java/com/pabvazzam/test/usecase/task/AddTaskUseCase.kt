package com.pabvazzam.test.usecase.task

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.TaskRepositoryImpl
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val taskRepositoryImpl: TaskRepositoryImpl
) {

    operator fun invoke(task: Task): Result<Boolean> {
        try {
            taskRepositoryImpl.addTask(task)
            return Result.success(true)
        } catch (error: Error) {
            return Result.failure(exception = NullPointerException())
        }
    }
}