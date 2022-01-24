package com.example.rsshool2021_android_task_pomodoro

import android.os.CountDownTimer
import androidx.recyclerview.widget.RecyclerView
import com.example.rsshool2021_android_task_pomodoro.databinding.RecyclerViewItemBinding

class StopwatchViewHolder(
    private val binding: RecyclerViewItemBinding,
    private val listener: StopwatchListener,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var timer: CountDownTimer? = null
    private var isFirstStart = true

    fun bind(stopwatch: Stopwatch) {
        binding.timer.text = stopwatch.displayTimeMs.displayTime()

            if (stopwatch.isStarted) {
                startTimer(stopwatch)
            } else {
                stopTimer()
            }

        initButtons(stopwatch)
    }

    private fun initButtons(stopwatch: Stopwatch) {
        binding.startStopButton.setOnClickListener {
            if (stopwatch.isStarted) {
                listener.stop(stopwatch.id, stopwatch.displayTimeMs, stopwatch.runningTimeMs)
            } else {
                listener.start(stopwatch.id)
            }
        }
        binding.delete.setOnClickListener {
            listener.delete(stopwatch.id)
        }
    }

    private fun startTimer(stopwatch: Stopwatch) {
        binding.startStopButton.text = "STOP"
        timer?.cancel()
        timer = getCountDownTimer(stopwatch)
        timer?.start()
    }

    private fun stopTimer() {
        binding.startStopButton.text = "START"
        timer?.cancel()
    }

    private fun getCountDownTimer(stopwatch: Stopwatch): CountDownTimer {
        return object : CountDownTimer(stopwatch.displayTimeMs, UNIT_TEN_MS) {

            var currentMs = stopwatch.displayTimeMs + stopwatch.systemStaticTimeMs

            override fun onTick(p0: Long) {
                stopwatch.runningTimeMs = currentMs - System.currentTimeMillis()
                binding.timer.text = stopwatch.runningTimeMs.displayTime()
            }

            override fun onFinish() {
                binding.timer.text = "[eq"
            }
        }
    }

    private companion object {
        private const val UNIT_TEN_MS = 100L
        private const val PERIOD = 1000L * 60L * 60L * 24L
    }
}