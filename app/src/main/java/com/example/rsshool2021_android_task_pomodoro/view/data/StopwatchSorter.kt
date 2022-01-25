package com.example.rsshool2021_android_task_pomodoro.view.data

import com.example.rsshool2021_android_task_pomodoro.model.Stopwatch
import com.example.rsshool2021_android_task_pomodoro.view.adapter.StopwatchAdapter

class StopwatchSorter {

    fun sort(
        stopwatches: MutableList<Stopwatch>,
        adapter: StopwatchAdapter,
        id: Int,
        currentTimeMs: Long?,
        systemStaticTimeMs: Long?,
        runningTimeMs: Long,
        isStarted: Boolean,
    ) {
        stopwatches.forEach {
            if (it.getId() == id && runningTimeMs == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.getId(),
                        currentTimeMs ?: it.getCurrentTimeMs(),
                        systemStaticTimeMs ?: it.getSystemStaticTimeMs(),
                        runningTimeMs,
                        isStarted,
                        it.getIsFinished(),
                        it.getBackGroundColor())
            } else if (it.getId() == id) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.getId(),
                        runningTimeMs,
                        systemStaticTimeMs ?: it.getSystemStaticTimeMs(),
                        0L,
                        isStarted,
                        it.getIsFinished(),
                        it.getBackGroundColor())
            } else if (it.getId() != id && it.getRunningTimeMs() == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.getId(),
                        it.getCurrentTimeMs(),
                        it.getSystemStaticTimeMs(),
                        it.getRunningTimeMs(),
                        false,
                        it.getIsFinished(),
                        it.getBackGroundColor())
            } else {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.getId(),
                        it.getRunningTimeMs(),
                        it.getSystemStaticTimeMs(),
                        0L,
                        false,
                        it.getIsFinished(),
                        it.getBackGroundColor())
            }
        }
        adapter.submitList(stopwatches.toList())
    }
}