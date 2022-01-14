package com.example.rsshool2021_android_task_pomodoro

data class Stopwatch(
    val id: Int,
    val currentMs: Long,
    val isStarted: Boolean
)
