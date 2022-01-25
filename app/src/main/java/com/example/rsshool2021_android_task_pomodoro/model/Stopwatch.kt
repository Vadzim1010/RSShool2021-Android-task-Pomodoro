package com.example.rsshool2021_android_task_pomodoro.model

data class Stopwatch(
    private val id: Int,
    private var currentTimeMs: Long,
    private var systemStaticTimeMs: Long,
    private var runningTimeMs: Long,
    private var isStarted: Boolean,
    private var isFinished: Boolean,
    private var backGroundColor: Int,
) {
    fun getId(): Int {
        return id
    }

    fun getCurrentTimeMs(): Long {
        return currentTimeMs
    }

    fun getSystemStaticTimeMs(): Long {
        return systemStaticTimeMs
    }

    fun getRunningTimeMs(): Long {
        return runningTimeMs
    }

    fun setRunningTimeMs(runningTimeMs: Long) {
        this.runningTimeMs = runningTimeMs
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

    fun getBackGroundColor(): Int {
        return backGroundColor
    }

    fun setBackGroundColor(backGroundColor: Int) {
        this.backGroundColor = backGroundColor
    }
}