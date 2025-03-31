package com.pabvazzam.test.ui.notifications

import androidx.work.DelegatingWorkerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDelegatingWorkerFactory @Inject constructor(
    workerFactory: AppWorkerFactory
) : DelegatingWorkerFactory() {
    init {
        addFactory(workerFactory)
    }
}