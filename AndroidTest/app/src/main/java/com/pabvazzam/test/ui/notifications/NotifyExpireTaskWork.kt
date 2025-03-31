package com.pabvazzam.test.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pabvazzam.test.MainActivity
import com.pabvazzam.test.R
import com.pabvazzam.test.TASK_STATUS_PENDING
import com.pabvazzam.test.repository.TaskRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

@HiltWorker
class NotifyExpireTaskWork @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val tasksRepository: TaskRepositoryImpl
) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        return try {
            withContext(Dispatchers.IO) {
                notifyForAlmostExpiredTasks()
            }
            Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    private fun notifyForAlmostExpiredTasks() {
        val tasks = tasksRepository.getTasks()
        tasks.forEach { task ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
            val formattedDate = dateFormat.parse(task.expirationDate)
            formattedDate?.let {
                val milliseconds = formattedDate.time - Calendar.getInstance().timeInMillis
                val minutes = ((milliseconds / 1000) / 60).toInt()
                if (minutes <= 15 && task.status == TASK_STATUS_PENDING && !task.expirationNotified) {
                    createNotification(task.title, minutes.toString())
                    task.expirationNotified = true
                    tasksRepository.editTask(task)
                }
            }
        }
    }

    private fun createNotificationChannel(notificationManager: NotificationManager?) {

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        )

        notificationManager?.createNotificationChannel(
            notificationChannel
        )
    }

    private fun createNotification(
        taskTitle: String,
        minutesUntilExpiration: String
    ) {
        val notificationManager: NotificationManager? =
            getSystemService(
                applicationContext,
                NotificationManager::class.java
            )

        createNotificationChannel(notificationManager)

        val mainActivityIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        var pendingIntentFlag by Delegates.notNull<Int>()
        pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE

        val mainActivityPendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            mainActivityIntent,
            pendingIntentFlag
        )

        val builder = NotificationCompat.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(applicationContext.getString(R.string.task_notification_title))
            .setContentText(
                applicationContext.getString(
                    R.string.task_notification_description,
                    taskTitle,
                    minutesUntilExpiration
                )
            )
            .setContentIntent(mainActivityPendingIntent)
            .setAutoCancel(true)

        notificationManager?.notify(NOTIFICATION_ID, builder.build())
    }

    companion object {
        const val NOTIFICATION_ID = 101
        const val NOTIFICATION_CHANNEL_NAME = "NotifyExpireTaskWork"
        const val NOTIFICATION_CHANNEL_ID = "NotificationChannelId"
    }
}