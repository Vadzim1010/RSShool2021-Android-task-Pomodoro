package com.example.rsshool2021_android_task_pomodoro.utils

import com.example.rsshool2021_android_task_pomodoro.features.timer.model.PomodoroTimer

const val START_TIME = "00:00:00"
const val INVALID = "INVALID"
const val COMMAND_START = "COMMAND_START"
const val COMMAND_STOP = "COMMAND_STOP"
const val COMMAND_ID = "COMMAND_ID"
const val CURRENT_TIME = "CURRENT_TIME"
const val TIMER_INTERVAL = 50L

fun Long.displayTime(): String {
    if (this <= 0L) {
        return START_TIME
    }
    val correctionMs =
        if (this != 0L) this + 999 else this // correction to doesn't count 0 second on timer

    val h = correctionMs / 1000 / 3600
    val m = correctionMs / 1000 % 3600 / 60
    val s = correctionMs / 1000 % 60

    return "${displaySlot(h)}:${displaySlot(m)}:${displaySlot(s)}"
}

private fun displaySlot(count: Long): String {
    return if (count / 10L > 0) {
        "$count"
    } else {
        "0$count"
    }
}

fun MutableList<PomodoroTimer>.sort( // to sort list of timers before submit
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
                0L,
                it.getStartedTimeMs(),
                false,
                it.getIsFinished()
            )
        }
    }
}

