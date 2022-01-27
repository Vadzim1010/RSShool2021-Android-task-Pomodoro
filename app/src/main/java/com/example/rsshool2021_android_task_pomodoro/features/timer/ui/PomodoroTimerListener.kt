package com.example.rsshool2021_android_task_pomodoro.features.timer.ui

interface PomodoroTimerListener {
    fun start(id: Int, currentTimeMs: Long)

    fun stop(id: Int, currentTimeMs: Long?)

    fun delete(id: Int)
}