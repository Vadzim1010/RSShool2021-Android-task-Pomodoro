package com.example.rsshool2021_android_task_pomodoro.features.foregroundservice

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.rsshool2021_android_task_pomodoro.eventbus.WorkingTimerEvent
import com.example.rsshool2021_android_task_pomodoro.features.timer.ui.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ServiceManager(
    private val context: Context,
) : LifecycleEventObserver {

    private var currentTime = -1L

    init {
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onTimerEvent(event: WorkingTimerEvent) {
        currentTime = event.currentMs + System.currentTimeMillis()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            onAppBackgrounded()
        }
        if (event == Lifecycle.Event.ON_START) {
            onAppForegrounded()
        }
    }

    private fun onAppForegrounded() {
        val stopIntent = Intent(context, ForegroundService::class.java)
        stopIntent.putExtra(COMMAND_ID, COMMAND_STOP)
        context.startService(stopIntent)
    }

    private fun onAppBackgrounded() {
        if (currentTime != -1L) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra(COMMAND_ID, COMMAND_START)
            startIntent.putExtra(CURRENT_TIME, currentTime)
            context.startService(startIntent)
        }
    }
}