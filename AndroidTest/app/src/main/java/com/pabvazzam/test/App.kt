package com.pabvazzam.test

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import com.pabvazzam.test.notifications.AppDelegatingWorkerFactory
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class App : Application() {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface WorkerFactoryEntryPoint {
        fun workerFactory(): AppDelegatingWorkerFactory
    }

    override fun onCreate() {
        super.onCreate()
        val workManagerConfiguration: Configuration = Configuration.Builder()
            .setWorkerFactory(
                EntryPoints.get(this, WorkerFactoryEntryPoint::class.java).workerFactory()
            )
            .setMinimumLoggingLevel(Log.VERBOSE)
            .build()
        WorkManager.initialize(this, workManagerConfiguration)
    }
}