package com.example.rsshool2021_android_task_pomodoro.eventbus

import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer

data class WorkingTimerEvent(
    var pomodoroTimer: PomodoroTimer? = null,
)