package com.example.rsshool2021_android_task_pomodoro.view.ui.main

interface StopwatchListener {
    fun start(id: Int)

    fun stop(id: Int, currentTimeMs: Long?, runningTimeMs: Long)

    fun delete(id: Int)
}