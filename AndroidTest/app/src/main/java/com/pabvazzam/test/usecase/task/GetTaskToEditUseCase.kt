package com.pabvazzam.test.usecase.task

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.TaskRepositoryImpl
import javax.inject.Inject

class GetTaskToEditUseCase @Inject constructor(
    private val taskRepositoryImpl: TaskRepositoryImpl
) {

    operator fun invoke(id: String): Result<Task> {
        val task = taskRepositoryImpl.getTask(id)
        return Result.success(task)
    }
}