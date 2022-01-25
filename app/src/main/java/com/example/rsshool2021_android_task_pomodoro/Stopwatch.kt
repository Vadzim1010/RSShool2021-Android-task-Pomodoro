package com.example.rsshool2021_android_task_pomodoro

import android.content.IntentSender

data class Stopwatch(
    val id: Int,
    var currentTimeMs: Long,
    var systemStaticTimeMs: Long,
    var runningTimeMs: Long,
    var isStarted: Boolean,
    var isFinished: Boolean,
    var backGroundColor: Int
)