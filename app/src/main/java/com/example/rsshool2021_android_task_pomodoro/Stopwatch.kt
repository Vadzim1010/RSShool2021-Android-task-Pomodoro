package com.example.rsshool2021_android_task_pomodoro

data class Stopwatch(
    val id: Int,
    var currentMs: Long,
    val isStarted: Boolean
)
