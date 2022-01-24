package com.example.rsshool2021_android_task_pomodoro

interface StopwatchListener {
    fun start(id: Int)

    fun stop(id: Int, displayTimeMs: Long?, runningTimeMs: Long)

    fun delete(id: Int)
}