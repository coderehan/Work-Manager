package com.cipl.workmanager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

// We have to extend our class with inbuilt Worker class inorder to work with work manager
class MyFirstWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        Thread.sleep(3000)      // Notification will come after 3 secs
         makeNotification("First Worker Title", "First Worker Description", applicationContext)
        return Result.success()
    }

    @SuppressLint("ResourceAsColor")
    private fun makeNotification(title: String, description: String, context: Context) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("first_work_manager", "worker", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, "first_work_manager")    // Pass the same notification channel id here also
            .setContentTitle(title)
            .setContentText(description)
            .setColor(R.color.purple_200)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
        notificationManager.notify(1, notificationBuilder)

    }

}