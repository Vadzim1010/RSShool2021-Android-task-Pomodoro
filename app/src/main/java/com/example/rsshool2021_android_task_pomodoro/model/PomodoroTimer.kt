package com.example.rsshool2021_android_task_pomodoro.model

data class PomodoroTimer(
    private val id: Int,
    private var currentTimeMs: Long, // to display this on screen
    private var runningTimeMs: Long, // to count time correct in timer(with System time)
    private var startedTimeMs: Long, // save started time to display it when timer is finished
    private var isStarted: Boolean, // to know timer state
    private var isFinished: Boolean, // to know when the time is up
) {
    fun getId(): Int {
        return id
    }

    fun getCurrentTimeMs(): Long {
        return currentTimeMs
    }

    fun setCurrentTimeMs(currentTimeMs: Long) {
        this.currentTimeMs = currentTimeMs
    }

    fun getRunningTimeMs(): Long {
        return runningTimeMs
    }

    fun setRunningTimeMs(runningTimeMs: Long) {
        this.runningTimeMs = runningTimeMs
    }

    fun getStartedTimeMs(): Long {
        return startedTimeMs
    }

    fun setStartedTimeMs(runningTimeMs: Long) {
        this.startedTimeMs = runningTimeMs
    }

    fun getIsStarted(): Boolean {
        return isStarted
    }

    fun setIsStarted(isStarted: Boolean) {
        this.isStarted = isStarted
    }

    fun getIsFinished(): Boolean {
        return isFinished
    }

    fun setIsFinished(isFinished: Boolean) {
        this.isFinished = isFinished
    }
}