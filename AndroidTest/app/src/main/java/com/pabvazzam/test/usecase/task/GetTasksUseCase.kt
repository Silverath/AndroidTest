package com.pabvazzam.test.usecase.task

import com.pabvazzam.test.data.Task
import com.pabvazzam.test.repository.TaskRepositoryImpl
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepositoryImpl: TaskRepositoryImpl
) {

    operator fun invoke(): Result<List<Task>> {
        val tasks = taskRepositoryImpl.getTasks()
        return Result.success(tasks)
    }
}