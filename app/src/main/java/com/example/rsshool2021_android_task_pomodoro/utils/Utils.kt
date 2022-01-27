package com.example.rsshool2021_android_task_pomodoro.features.timer.ui

import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer

const val START_TIME = "00:00:00"
const val INVALID = "INVALID"
const val COMMAND_START = "COMMAND_START"
const val COMMAND_STOP = "COMMAND_STOP"
const val COMMAND_ID = "COMMAND_ID"
const val CURRENT_TIME = "CURRENT_TIME"
const val INTERVAL = 100L

fun Long.displayTime(): String {
    if (this <= 0L) {
        return START_TIME
    }
    val h = this / 1000 / 3600
    val m = this / 1000 % 3600 / 60
    val s = this / 1000 % 60

    return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
}

fun MutableList<PomodoroTimer>.getCurrentTime(): Long? {
    var currentTime: Long? = null
    this.forEach {
        if (it.getIsStarted()) {
            currentTime = it.getCurrentTimeMs() + System.currentTimeMillis()
        }
    }
    return currentTime
}

fun MutableList<PomodoroTimer>.sort(
    id: Int,
    currentTimeMs: Long?,
    runningTimeMs: Long?,
    isStarted: Boolean,
) {
    this.forEach {
        if (it.getId() == id) {
            this[this.indexOf(it)] = PomodoroTimer(
                it.getId(),
                currentTimeMs ?: it.getCurrentTimeMs(),
                runningTimeMs ?: it.getRunningTimeMs(),
                it.getStartedTimeMs(),
                isStarted,
                it.getIsFinished()
            )
        } else {
            this[this.indexOf(it)] = PomodoroTimer(
                it.getId(),
                it.getCurrentTimeMs(),
                it.getRunningTimeMs(),
                it.getStartedTimeMs(),
                false,
                it.getIsFinished()
            )
        }
    }
}

private fun displaySlot(count: Long): String {
    return if (count / 10L > 0) {
        "$count"
    } else {
        "0$count"
    }
}