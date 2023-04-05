package com.cipl.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.cipl.workmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Constraints are basically used to check internet service before getting data
        // Here first we will check if UNMETERED i.e. wifi is ON, we will get notification. If wifi is OFF, we won't get notification
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build()

//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyFirstWorker::class.java).setConstraints(constraints).build()
//        val secondWorkRequest = OneTimeWorkRequest.Builder(MySecondWorker::class.java).setConstraints(constraints).build()

        // Minimum 15 mins is required for periodic work request
        val periodicWorkRequest = PeriodicWorkRequest.Builder(MyFirstWorker::class.java, 15, TimeUnit.MINUTES).addTag("first_work").build()

        // This is used for only first request. It means we will get notification for only 1 time after clicking button
//        binding.btnStartWork.setOnClickListener {
//            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
//        }

        //  This is used for getting notification in sequence. It means first we will get 1 notification and after few secs we will get another notification in sequence.
//        binding.btnStartWork.setOnClickListener {
//            WorkManager.getInstance().beginWith(oneTimeWorkRequest).then(secondWorkRequest).enqueue()
//        }

        // This is used for getting notification after some period of time using PeriodicWorkRequest
        binding.btnStartWork.setOnClickListener {
            WorkManager.getInstance().enqueueUniquePeriodicWork("first_work", ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest)
        }

    }


}