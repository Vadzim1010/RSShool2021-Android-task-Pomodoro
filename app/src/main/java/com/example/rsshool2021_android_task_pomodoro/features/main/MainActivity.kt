package com.example.rsshool2021_android_task_pomodoro.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rsshool2021_android_task_pomodoro.R
import com.example.rsshool2021_android_task_pomodoro.features.foregroundservice.ServiceManager
import com.example.rsshool2021_android_task_pomodoro.features.timer.ui.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(ServiceManager(this))

        if (savedInstanceState == null) {
            val fragment = PomodoroTimerFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
        }
    }
}