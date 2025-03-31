package com.pabvazzam.test.ui.notifications

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.pabvazzam.test.repository.TaskRepositoryImpl
import javax.inject.Inject


class AppWorkerFactory @Inject constructor(
    private val taskRepository: TaskRepositoryImpl,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            NotifyExpireTaskWork::class.java.name -> NotifyExpireTaskWork(
                appContext,
                workerParameters,
                taskRepository
            )

            else -> null
        }
    }
}