package com.example.rsshool2021_android_task_pomodoro

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
            if (it.id == id && runningTimeMs == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        currentTimeMs ?: it.currentTimeMs,
                        systemStaticTimeMs ?: it.systemStaticTimeMs,
                        runningTimeMs,
                        isStarted,
                        it.isFinished,
                        it.backGroundColor)
            } else if (it.id == id) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        runningTimeMs,
                        systemStaticTimeMs ?: it.systemStaticTimeMs,
                        0L,
                        isStarted,
                        it.isFinished,
                        it.backGroundColor)
            } else if (it.id != id && it.runningTimeMs == 0L) {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        it.currentTimeMs,
                        it.systemStaticTimeMs,
                        it.runningTimeMs,
                        false,
                        it.isFinished,
                        it.backGroundColor)
            } else {
                stopwatches[stopwatches.indexOf(it)] =
                    Stopwatch(it.id,
                        it.runningTimeMs,
                        it.systemStaticTimeMs,
                        0L,
                        false,
                        it.isFinished,
                        it.backGroundColor)
            }
        }
        adapter.submitList(stopwatches.toList())
    }
}