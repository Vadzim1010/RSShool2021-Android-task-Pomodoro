package com.example.rsshool2021_android_task_pomodoro

data class Stopwatch(
    val id: Int,
    var displayTimeMs: Long,
    var systemStaticTimeMs: Long,
    var runningTimeMs: Long,
    var isStarted: Boolean
)
